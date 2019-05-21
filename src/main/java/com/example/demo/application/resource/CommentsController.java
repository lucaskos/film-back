package com.example.demo.application.resource;

import com.example.demo.application.commands.CommentCommand;
import com.example.demo.application.services.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/comment")
@CrossOrigin
public class CommentsController {

    private CommentService commentService;

    public CommentsController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/add")
    public ResponseEntity addComment(CommentCommand commentCommand) {
        Object o = commentService.addComment(commentCommand);
        return new ResponseEntity(new Object(), HttpStatus.OK);
    }

}
