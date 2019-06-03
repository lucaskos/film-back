package com.example.demo.application.DTO;

import com.example.demo.application.commands.ObjectType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CommentsDTO {

	private Long id;
	private Long entityId; //todo entityDTO
	private String entityType;
	private LocalDate createdDate;
	private int depth;
	private Long parentCommentId;
	private String title;
	private String text;
	private UserDTO userId;

}
