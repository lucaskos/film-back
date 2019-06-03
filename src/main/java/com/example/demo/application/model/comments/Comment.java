package com.example.demo.application.model.comments;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@Data
@MappedSuperclass
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "created_date")
	private Date createdDate;
	@Column(name = "depth")
	private int depth;
	@Column(name = "parent_comment_id")
	private Long parentCommentId;
	@Column(name = "text", nullable = false, columnDefinition = "text")
	private String text;
	@Column(name = "title", nullable = false)
	private String title;
}