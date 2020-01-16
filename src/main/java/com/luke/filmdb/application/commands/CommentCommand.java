package com.luke.filmdb.application.commands;

import com.luke.filmdb.application.DTO.CommentDTO;
import lombok.Data;

@Data
public class CommentCommand {

	private long id;
	private String entityType;
	private CommentDTO commentDTO;

}
