package com.example.demo.application.services;

import com.example.demo.application.DTO.CommentsDTO;
import com.example.demo.application.DTO.mapper.CommentMapper;
import com.example.demo.application.commands.ObjectType;
import com.example.demo.application.model.Film;
import com.example.demo.application.model.comments.Comment;
import com.example.demo.application.model.comments.FilmComment;
import com.example.demo.application.repository.FilmCommentsRepo;
import com.example.demo.application.repository.FilmRepo;
import com.example.demo.application.repository.PersonRepo;
import liquibase.util.CollectionUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
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

	private CommentsDTO getDetails(FilmComment comment) {
		Set<FilmComment> subComments = comment.getSubComments();
		CommentsDTO commentsDTO = commentMapper.commentToCommentDTO(comment);
		Set<CommentsDTO> subCommentsSet = new HashSet<>();

		if (!CollectionUtils.isEmpty(subComments)) {

			for (FilmComment subComment : subComments) {
				CommentsDTO subCommentDTO = commentMapper.commentToCommentDTO(subComment);
				commentsDTO.getSubComments().add(subCommentDTO);
				CommentsDTO commentDetails = getDetails(subComment);

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

			commentsDTO.getSubComments().addAll(subCommentsSet);
		}

		return commentsDTO;
	}

}
