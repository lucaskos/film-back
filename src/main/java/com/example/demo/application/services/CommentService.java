package com.example.demo.application.services;

import com.example.demo.application.DTO.CommentsDTO;
import com.example.demo.application.DTO.mapper.CommentMapper;
import com.example.demo.application.commands.ObjectType;
import com.example.demo.application.model.Film;
import com.example.demo.application.model.Person;
import com.example.demo.application.model.comments.Comment;
import com.example.demo.application.model.comments.FilmComment;
import com.example.demo.application.model.comments.PersonComment;
import com.example.demo.application.repository.FilmCommentsRepo;
import com.example.demo.application.repository.FilmRepo;
import com.example.demo.application.repository.PersonCommentsRepo;
import com.example.demo.application.repository.PersonRepo;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CommentService {

	private FilmRepo filmDao;
	private PersonRepo personRepo;
	private CommentMapper commentMapper;
	private FilmCommentsRepo filmCommentsRepo;
	private PersonCommentsRepo personCommentsRepo;

	public CommentService(FilmRepo filmDao,
						  PersonRepo personRepo,
						  CommentMapper commentMapper,
						  FilmCommentsRepo filmCommentsRepo,
						  PersonCommentsRepo personCommentsRepo) {
		this.filmDao = filmDao;
		this.personRepo = personRepo;
		this.commentMapper = commentMapper;
		this.filmCommentsRepo = filmCommentsRepo;
		this.personCommentsRepo = personCommentsRepo;
	}

	//	@Transactional
	public Object addComment(CommentsDTO commentDto) {
		JpaRepository jpaRepository;

		if (ObjectType.FILM.toString().equals(commentDto.getEntityType())) {
			jpaRepository = filmDao;
			Film film = ((FilmRepo) jpaRepository).getFilmDetails(commentDto.getEntityId()).get();

			if (film == null) {
				throw new RuntimeException("Brak filmu dla zapytania: " + commentDto.toString());
			}

			FilmComment filmComment = commentMapper.commentCommandToFilmCommentEntity(commentDto);
			FilmComment save = filmCommentsRepo.save(filmComment);

			return save;
		}
		if (ObjectType.PERSON.toString().equals(commentDto.getEntityType())) {
			jpaRepository = personRepo;
			Person person = personRepo.getOne(commentDto.getEntityId());

			if (person == null) {
				throw new RuntimeException("Brak osoby dla zapytania: " + commentDto.toString());
			}

			return new Object();
		}

		return new Object();
	}

	public Object findComment(Long id) {
		FilmComment one = filmCommentsRepo.getOne(id);
		return one;
	}

	public Object findCommentDetails(Long id) {
		FilmComment one = filmCommentsRepo.getOne(id);
		CommentsDTO details = getFilmCommentDetails(one);
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
	private CommentsDTO getFilmCommentDetails(Comment comment) {
		CommentsDTO mainCommentDTO = commentMapper.commentToCommentDTO(comment);
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

	public List<CommentsDTO> findEntityComments(CommentsDTO commentsDTO) {

		if(ObjectType.FILM.toString().equals(commentsDTO.getEntityType())) {

			Optional<List<FilmComment>> byFilmId = filmCommentsRepo.findByFilmIdAndParentCommentIdIsNull(filmDao.getOne(commentsDTO.getEntityId()));
			List<FilmComment> filmComments = byFilmId.get();
			List<CommentsDTO> comments = new ArrayList<>();
			filmComments.forEach(comment -> comments.add(commentMapper.commentToCommentDTO(comment)));

			return comments;
		} else {
			return null;
		}

	}
}
