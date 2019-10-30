package com.example.demo.application.DTO;

import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class CommentsDTO {

    private Long id;
    private Long entityId;
    private String entityType;
    private LocalDate createdDate;
    private int depth;
    private CommentsDTO parentCommentId;
    private Set<CommentsDTO> subComments = new HashSet<>();
    private String title;
    private String text;
    private LoginUserDTO userId;

}
