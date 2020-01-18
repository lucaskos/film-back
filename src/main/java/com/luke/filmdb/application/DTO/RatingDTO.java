package com.luke.filmdb.application.DTO;

import com.luke.filmdb.application.commands.ObjectType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RatingDTO {
    private ObjectType objectType;
    private Long objectId;
}
