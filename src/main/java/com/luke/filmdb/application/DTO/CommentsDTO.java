package com.luke.filmdb.application.DTO;

import com.luke.filmdb.application.DTO.user.LoginUserDTO;
import lombok.Data;

import java.time.LocalDate;

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
