package com.luke.filmdb.application.DTO;

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
    private String title;
    private String text;
    private LoginUserDTO user;

    @Override
    public String toString() {
        return "CommentsDTO{" +
                "id=" + id +
                ", entityId=" + entityId +
                ", entityType='" + entityType + '\'' +
                ", createdDate=" + createdDate +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", userId=" + user +
                '}';
    }
}
