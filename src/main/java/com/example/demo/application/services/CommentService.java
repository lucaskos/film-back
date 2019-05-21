package com.example.demo.application.services;

import com.example.demo.application.DTO.mapper.CommentMapper;
import com.example.demo.application.commands.CommentCommand;
import com.example.demo.application.commands.ObjectType;
import com.example.demo.application.model.Film;
import com.example.demo.application.model.FilmComment;
import com.example.demo.application.repository.FilmCommentsRepo;
import com.example.demo.application.repository.FilmRepo;
import com.example.demo.application.repository.PersonRepo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
    public Object addComment(CommentCommand commentCommand){
        JpaRepository jpaRepository;
        if(ObjectType.FILM.toString().equals(commentCommand.getEntityType())) {
            jpaRepository = filmDao;
	        Film one = ((FilmRepo) jpaRepository).getFilmDetails(commentCommand.getCommentsDTO().getEntityId()).get();
	        if (one == null) {
	        	throw new RuntimeException("brak filmu dla zapytanie " + commentCommand.toString());
	        }
	        FilmComment filmComment = commentMapper.commentCommandToFilmCommentEntity(commentCommand.getCommentsDTO());
	        filmComment.setFilmId(one);
	        FilmComment save = filmCommentsRepo.save(filmComment);
	        return save;
        }
	    if (ObjectType.PERSON.toString().equals(commentCommand.getEntityType())) {
		    return new Object();
	    }

//        jpaRepository.saveUser()
        return new Object();
    }

    public Object findComment(Long id) {
	    Optional<FilmComment> byId = filmCommentsRepo.findById(id);
	    return byId.get();
    }

}
