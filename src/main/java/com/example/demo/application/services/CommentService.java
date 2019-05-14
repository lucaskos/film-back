package com.example.demo.application.services;

import com.example.demo.application.commands.CommentCommand;
import com.example.demo.application.commands.ObjectType;
import com.example.demo.application.repository.FilmRepo;
import com.example.demo.application.repository.PersonRepo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    private FilmRepo filmDao;
    private PersonRepo personRepo;

    public CommentService(FilmRepo filmDao, PersonRepo personRepo) {
        this.filmDao = filmDao;
        this.personRepo = personRepo;
    }

    public Object addComment(CommentCommand commentCommand){
        JpaRepository jpaRepository;
        if(commentCommand.getObjectType().equals(ObjectType.FILM)) {
            jpaRepository = filmDao;
        }
        if(commentCommand.getObjectType().equals(ObjectType.PERSON)) {
            jpaRepository = personRepo;
        }

//        jpaRepository.save()
        return new Object();
    }

}
