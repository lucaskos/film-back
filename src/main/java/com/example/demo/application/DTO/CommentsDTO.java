package com.example.demo.application.DTO;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CommentsDTO {

    private Long id;
    private Object entityId; //todo entityDTO
    private LocalDate createdDate;
    private int depth;
    private Long parentCommentId;
    private String title;
    private String text;
    private int userId;

}
