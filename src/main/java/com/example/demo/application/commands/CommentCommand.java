package com.example.demo.application.commands;

import com.example.demo.application.DTO.CommentsDTO;
import lombok.Data;

@Data
public class CommentCommand {

    private long id;
    private String entityType;
    private CommentsDTO commentsDTO;

}
