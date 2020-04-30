package com.luke.filmdb.application.resources;

import com.luke.filmdb.application.DTO.CommentDTO;
import com.luke.filmdb.application.services.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/comment")
@CrossOrigin
public class CommentsController {

    private CommentService commentService;

    public CommentsController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/add")
    public ResponseEntity addComment(@RequestBody CommentDTO commentDTO) {
        Object savedComment = commentService.addComment(commentDTO);
        return new ResponseEntity(savedComment, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity getComment(@PathVariable(name = "id") Long commentId) {
        Object comment = commentService.findComment(commentId);
        return new ResponseEntity(comment, HttpStatus.OK);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity getCommentDetails(@PathVariable(name = "id") Long commentId) {
        return new ResponseEntity(commentService.findCommentDetails(commentId), HttpStatus.OK);
    }

    @PostMapping("/list")
    public ResponseEntity getEntityComments(@RequestBody CommentDTO commentDTO) {
        return new ResponseEntity(commentService.findEntityComments(commentDTO), HttpStatus.OK);
    }
}
