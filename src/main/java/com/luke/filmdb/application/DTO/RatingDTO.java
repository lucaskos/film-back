package com.luke.filmdb.application.DTO;

import com.luke.filmdb.application.commands.ObjectType;
import lombok.Data;

@Data
public class RatingDTO {
    private ObjectType objectType;
    private Long objectId;
}
