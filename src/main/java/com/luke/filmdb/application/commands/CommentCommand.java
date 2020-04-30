package com.luke.filmdb.application.commands;

import com.luke.filmdb.application.DTO.CommentDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentCommand {

    private long id;
    private String entityType;
    private CommentDTO commentDTO;

}
