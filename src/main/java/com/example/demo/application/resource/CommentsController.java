package com.example.demo.application.resource;

import com.example.demo.application.DTO.CommentsDTO;
import com.example.demo.application.commands.CommentCommand;
import com.example.demo.application.services.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public ResponseEntity addComment(@RequestBody CommentsDTO commentsDTO) {
        Object savedComment = commentService.addComment(commentsDTO);
        return new ResponseEntity(savedComment, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity getComment(@PathVariable(name = "id") Long commentId) {
        return new ResponseEntity(commentService.findComment(commentId), HttpStatus.OK);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity getCommentDetails(@PathVariable(name = "id") Long commentId) {
        return new ResponseEntity(commentService.findCommentDetails(commentId), HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity getEntityComments(@RequestBody CommentsDTO commentsDTO) {
        return new ResponseEntity(commentService.findEntityComments(commentsDTO), HttpStatus.OK);
    }
}
