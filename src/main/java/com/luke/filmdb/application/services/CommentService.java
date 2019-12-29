package com.luke.filmdb.application.services;

import com.luke.filmdb.application.DTO.CommentsDTO;
import com.luke.filmdb.application.DTO.mapper.CommentMapper;
import com.luke.filmdb.application.DTO.mapper.UserMapper;
import com.luke.filmdb.application.commands.ObjectType;
import com.luke.filmdb.application.model.Film;
import com.luke.filmdb.application.model.Person;
import com.luke.filmdb.application.model.comments.Comment;
import com.luke.filmdb.application.model.comments.FilmComment;
import com.luke.filmdb.application.model.comments.PersonComment;
import com.luke.filmdb.application.model.user.User;
import com.luke.filmdb.application.repository.FilmCommentsRepo;
import com.luke.filmdb.application.repository.FilmRepo;
import com.luke.filmdb.application.repository.PersonCommentsRepo;
import com.luke.filmdb.application.repository.PersonRepo;
import com.luke.filmdb.application.resource.filter.UserNotFoundException;
import com.luke.filmdb.commons.SecurityUtil;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CommentService {

    private final Logger logger = LoggerFactory.getLogger(CommentService.class);
    private final FilmRepo filmDao;
    private final PersonRepo personRepo;
    private final CommentMapper commentMapper;
    private final FilmCommentsRepo filmCommentsRepo;
    private final PersonCommentsRepo personCommentsRepo;
    private final UserMapper userMapper;
    private final UserService userService;

    //	@Transactional
    public Comment addComment(CommentsDTO commentDto) {
        Comment comment;

        if (ObjectType.FILM.toString().equals(commentDto.getEntityType())) {
            comment = addFilmComment(commentDto);
        } else if (ObjectType.PERSON.toString().equals(commentDto.getEntityType())) {
            comment = addPersonComment(commentDto);
        } else {
            logger.error("Unknown comment type");
            throw new RuntimeException("Unknown comment type");
        }


        return comment;
    }

    private Comment addFilmComment(CommentsDTO commentDto) {

        Film film = this.filmDao.getFilmDetails(commentDto.getEntityId()).orElseThrow(() ->
                new NotFoundException("Couldn't find film : " + commentDto.getEntityId()));


        FilmComment filmComment = commentMapper.commentCommandToFilmCommentEntity(commentDto);
        User currentlyLoggedUser = null;
        try {
            currentlyLoggedUser = userService.getCurrentlyLoggedUser();
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }
        filmComment.setOwner(currentlyLoggedUser);
        filmComment.setFilm(film);
        return filmCommentsRepo.save(filmComment);
    }

    private Comment addPersonComment(CommentsDTO commentDto) {
        Person person = personRepo.getPersonDetails(commentDto.getEntityId()).orElseThrow(() ->
                new NotFoundException("Couldn't find person : " + commentDto.getEntityId()));
//        personRepo.getPersonDetails(commentDto.getEntityId());

        PersonComment personComment = commentMapper.commentCommandToPersonCommandEntity(commentDto);
        personComment.setPerson(person);
        return personCommentsRepo.save(personComment);
    }

    public Object findComment(Long id) {
        return filmCommentsRepo.getOne(id);
    }

    public Object findCommentDetails(Long id) {
        FilmComment one = filmCommentsRepo.getOne(id);
        return getFilmCommentDetails(one);
    }

    private Object getFilmCommentDetails(FilmComment one) {
        return commentMapper.commentToCommentDTO(one);
    }


//    /**
//     * Getting main comment with all subComments and subComments children. We getting hierarchy in a manner:
//     * comment
//     * - subComment
//     * - subComment2 (child of subComment)
//     * - subComment3 (child of subComment2)
//     *
//     * @param comment
//     * @return
//     */
//    private CommentsDTO getFilmCommentDetails(Comment comment) { //todo testy INSERT INTO "PUBLIC"."FILM_COMMENT"("ID","FILM","DEPTH","PARENT_COMMENT","TEXT","TITLE","OWNER")VALUES(11,1,0,9,'test','test',1), wiecej niz > 1 komentarz w hierarchi
//        CommentsDTO mainCommentDTO = commentMapper.commentToCommentDTO(comment);
////        mainCommentDTO.setUserId(userMapper.userToLoginUserDTO(comment.getOwner()));
//        Set<FilmComment> mainCommentSubComments = ((FilmComment) comment).getSubComments();
//        Set<CommentsDTO> mainCommentSubCommentsDTO = new HashSet<>();
//        Set<Long> idSet = new HashSet<>();
//
//        if (!CollectionUtils.isEmpty(mainCommentSubComments)) {
//
//            for (FilmComment subComment : mainCommentSubComments) {
//
//                idSet.add(subComment.getId());
//
//                CommentsDTO subCommentDTO = commentMapper.commentToCommentDTO(subComment);
//                mainCommentDTO.getSubComments().add(subCommentDTO);
//                CommentsDTO commentDetails = getFilmCommentDetails(subComment);
//
//
//                checkIfCommentsAreDifferentAndAdd(mainCommentDTO, mainCommentSubCommentsDTO, commentDetails);
//            }
//
//            List<Long> collect = mainCommentDTO.getSubComments().stream().map(CommentsDTO::getId).collect(Collectors.toList());
//
//            if (!collect.contains(mainCommentDTO)) {
//                mainCommentDTO.getSubComments().addAll(mainCommentSubCommentsDTO);
//            }
//        }
//
//        return mainCommentDTO;
//    }
//
//    /**
//     * @param commentsDTO    - main comment processed.
//     * @param subCommentsSet - main comment children.
//     * @param commentDetails - the child comment of the comment iterated list of subComments.
//     */
//    private void checkIfCommentsAreDifferentAndAdd(CommentsDTO commentsDTO,
//                                                   Set<CommentsDTO> subCommentsSet,
//                                                   CommentsDTO commentDetails) {
//
//        commentsDTO.getSubComments().forEach(next -> {
//            if (commentDetails != null && next.getId().equals(commentDetails.getId())) {
//                next.getSubComments().addAll(commentDetails.getSubComments());
//            } else {
//                subCommentsSet.add(commentDetails);
//            }
//        });
//    }

    public List<CommentsDTO> findEntityComments(CommentsDTO commentsDTO) {

        if (ObjectType.FILM.toString().equals(commentsDTO.getEntityType())) {

            Optional<List<FilmComment>> filmCommentsByFilmId =
                    filmCommentsRepo.findByFilmId(filmDao.getOne(commentsDTO.getEntityId()).getId());

            List<FilmComment> filmComments = filmCommentsByFilmId.orElseThrow(
                    () -> new NotFoundException("For film : " + commentsDTO.getEntityId() + " no comments found"));

            List<CommentsDTO> comments = new ArrayList<>();
            filmComments.forEach(comment -> comments.add(commentMapper.commentToCommentDTO(comment)));

            return comments;
        } else if (ObjectType.PERSON.toString().equals(commentsDTO.getEntityType())) {
            Person one = personRepo.getOne(commentsDTO.getEntityId());
            //must todo wywalic Optional<List<PersonComment>> byFilmId = personCommentsRepo.findByFilmId(one.getId());
            Optional<List<PersonComment>> byFilmId = Optional.ofNullable(personCommentsRepo.findAll());

            List<PersonComment> personComments = byFilmId.orElseThrow(
                    () -> new NotFoundException("For person: " + commentsDTO.getEntityId() + " no comment found")
            );

            List<CommentsDTO> commentsDTOS = new ArrayList<>();
            personComments.forEach(comment -> commentsDTOS.add(commentMapper.commentToCommentDTO(comment)));
            return commentsDTOS;
        } else {
            logger.error("Unknown comment type.");
            //throw new RuntimeException("Unknown object type.");
            return null;
        }
    }
}
