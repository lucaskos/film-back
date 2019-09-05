package com.example.demo.application.DTO;

import com.example.demo.application.commands.ObjectType;
import lombok.Data;

@Data
public class RatingDTO {
    private ObjectType objectType;
    private Long objectId;
}
