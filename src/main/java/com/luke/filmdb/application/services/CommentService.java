package com.luke.filmdb.application.services;

import com.luke.filmdb.application.DTO.CommentsDTO;
import com.luke.filmdb.application.DTO.mapper.CommentMapper;
import com.luke.filmdb.application.DTO.mapper.UserMapper;
import com.luke.filmdb.application.commands.ObjectType;
import com.luke.filmdb.application.model.Person;
import com.luke.filmdb.application.model.comments.Comment;
import com.luke.filmdb.application.model.comments.FilmComment;
import com.luke.filmdb.application.repository.FilmCommentsRepo;
import com.luke.filmdb.application.repository.FilmRepo;
import com.luke.filmdb.application.repository.PersonCommentsRepo;
import com.luke.filmdb.application.repository.PersonRepo;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class CommentService {

    private final FilmRepo filmDao;
    private final PersonRepo personRepo;
    private final CommentMapper commentMapper;
    private final FilmCommentsRepo filmCommentsRepo;
    private final PersonCommentsRepo personCommentsRepo;
    private final UserMapper userMapper;

    //	@Transactional
    public Comment addComment(CommentsDTO commentDto) {
        Comment comment = null;

        if (ObjectType.FILM.toString().equals(commentDto.getEntityType())) {
            comment = addFilmComment(commentDto);
        }

        if (ObjectType.PERSON.toString().equals(commentDto.getEntityType())) {
            comment = addPersonComment(commentDto);
        }

        return comment;
    }

    private Comment addFilmComment(CommentsDTO commentDto) {

        this.filmDao.getFilmDetails(commentDto.getEntityId()).orElseThrow(() ->
                new NotFoundException("Couldn't find film : " + commentDto.getEntityId()));

        FilmComment filmComment = commentMapper.commentCommandToFilmCommentEntity(commentDto);
        return filmCommentsRepo.save(filmComment);
    }

    private Comment addPersonComment(CommentsDTO commentDto) {
        Person person = personRepo.getPersonDetails(commentDto.getEntityId()).orElseThrow(() ->
                new NotFoundException("Couldn't find person : " + commentDto.getEntityId()));
//        personRepo.getPersonDetails(commentDto.getEntityId());

        return null;
    }

    public Object findComment(Long id) {
        return filmCommentsRepo.getOne(id);
    }

    public Object findCommentDetails(Long id) {
        FilmComment one = filmCommentsRepo.getOne(id);
        return getFilmCommentDetails(one);
    }

    /**
     * Getting main comment with all subComments and subComments children. We getting hierarchy in a manner:
     * comment
     * - subComment
     * - subComment2 (child of subComment)
     * - subComment3 (child of subComment2)
     *
     * @param comment
     * @return
     */
    private CommentsDTO getFilmCommentDetails(Comment comment) {
        CommentsDTO mainCommentDTO = commentMapper.commentToCommentDTO(comment);
//        mainCommentDTO.setUserId(userMapper.userToLoginUserDTO(comment.getOwner()));
        Set<FilmComment> mainCommentSubComments = ((FilmComment) comment).getSubComments();
        Set<CommentsDTO> mainCommentSubCommentsDTO = new HashSet<>();

        if (!CollectionUtils.isEmpty(mainCommentSubComments)) {

            for (FilmComment subComment : mainCommentSubComments) {

                CommentsDTO subCommentDTO = commentMapper.commentToCommentDTO(subComment);
                mainCommentDTO.getSubComments().add(subCommentDTO);
                CommentsDTO commentDetails = getFilmCommentDetails(subComment);

                checkIfCommentsAreDifferentAndAdd(mainCommentDTO, mainCommentSubCommentsDTO, commentDetails);
            }

            mainCommentDTO.getSubComments().addAll(mainCommentSubCommentsDTO);
        }

        return mainCommentDTO;
    }

    /**
     * @param commentsDTO    - main comment processed.
     * @param subCommentsSet - main comment children.
     * @param commentDetails - the child comment of the comment iterated list of subComments.
     */
    private void checkIfCommentsAreDifferentAndAdd(CommentsDTO commentsDTO,
                                                   Set<CommentsDTO> subCommentsSet,
                                                   CommentsDTO commentDetails) {

        commentsDTO.getSubComments().forEach(next -> {
            if (commentDetails != null && next.getId().equals(commentDetails.getId())) {
                next.getSubComments().addAll(commentDetails.getSubComments());
            } else {
                subCommentsSet.add(commentDetails);
            }
        });
    }

    public List<CommentsDTO> findEntityComments(CommentsDTO commentsDTO) {

        if (ObjectType.FILM.toString().equals(commentsDTO.getEntityType())) {

            Optional<List<FilmComment>> filmCommentsByFilmId =
                    filmCommentsRepo.findByFilmIdAndParentCommentIdIsNull(filmDao.getOne(commentsDTO.getEntityId()).getId());

            List<FilmComment> filmComments = filmCommentsByFilmId.orElseThrow(
                    () -> new NotFoundException("For film : " + commentsDTO.getEntityId() + " no comments found"));

            List<CommentsDTO> comments = new ArrayList<>();
            filmComments.forEach(comment -> comments.add(commentMapper.commentToCommentDTO(comment)));

            return comments;
        } else {
            return Collections.emptyList();
        }
    }
}
