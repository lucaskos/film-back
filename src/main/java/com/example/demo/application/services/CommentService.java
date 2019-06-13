package com.example.demo.application.services;

import com.example.demo.application.DTO.CommentsDTO;
import com.example.demo.application.DTO.mapper.CommentMapper;
import com.example.demo.application.model.comments.Comment;
import com.example.demo.application.model.comments.FilmComment;
import com.example.demo.application.repository.FilmCommentsRepo;
import com.example.demo.application.repository.FilmRepo;
import com.example.demo.application.repository.PersonRepo;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Service
public class CommentService {

	private FilmRepo filmDao;
	private PersonRepo personRepo;
	private CommentMapper commentMapper;
	private FilmCommentsRepo filmCommentsRepo;

	public CommentService(FilmRepo filmDao, PersonRepo personRepo, CommentMapper commentMapper, FilmCommentsRepo filmCommentsRepo) {
		this.filmDao = filmDao;
		this.personRepo = personRepo;
		this.commentMapper = commentMapper;
		this.filmCommentsRepo = filmCommentsRepo;
	}

	@Transactional
	public Object addComment(CommentsDTO commentDto) {
//		JpaRepository jpaRepository;
//		if (ObjectType.FILM.toString().equals(commentDto.getEntityType())) {
//			jpaRepository = filmDao;
//			Film one = ((FilmRepo) jpaRepository).getFilmDetails(commentDto.getEntityId()).get();
//			if (one == null) {
//				throw new RuntimeException("brak filmu dla zapytanie " + commentDto.toString());
//			}
//			FilmComment filmComment = commentMapper.commentCommandToFilmCommentEntity(commentDto);
//			filmComment.setFilmId(one);
//			FilmComment save = filmCommentsRepo.save(filmComment);
//			return save;
//		}
//		if (ObjectType.PERSON.toString().equals(commentDto.getEntityType())) {
//			return new Object();
//		}

//        jpaRepository.saveUser()
		return new Object();
	}

	public Object findComment(Long id) {
		FilmComment one = filmCommentsRepo.getOne(id);
		return one;
	}

	public Object findCommentDetails(Long id) {
		FilmComment one = filmCommentsRepo.getOne(id);
		CommentsDTO details = getDetails(one);
		return details;
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
	private CommentsDTO getDetails(Comment comment) {
		CommentsDTO mainCommentDTO = commentMapper.commentToCommentDTO(comment);
		Set<FilmComment> mainCommentSubComments = ((FilmComment) comment).getSubComments();
		Set<CommentsDTO> mainCommentSubCommentsDTO = new HashSet<>();

		if (!CollectionUtils.isEmpty(mainCommentSubComments)) {

			for (FilmComment subComment : mainCommentSubComments) {

				CommentsDTO subCommentDTO = commentMapper.commentToCommentDTO(subComment);
				mainCommentDTO.getSubComments().add(subCommentDTO);
				CommentsDTO commentDetails = getDetails(subComment);

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
		Iterator<CommentsDTO> iterator = commentsDTO.getSubComments().iterator();

		while (iterator.hasNext()) {
			CommentsDTO next = iterator.next();

			if (commentDetails != null && next.getId().equals(commentDetails.getId())) {
				next.getSubComments().addAll(commentDetails.getSubComments());
			} else {
				subCommentsSet.add(commentDetails);
			}

		}
	}

}
