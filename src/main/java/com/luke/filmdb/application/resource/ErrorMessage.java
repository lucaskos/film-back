package com.luke.filmdb.application.resource;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessage extends Throwable {

    private Integer code;
    private String message;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Error message {\n");
        sb.append(" code: ").append(getCode()).append("\n");
        sb.append(" message: ").append(getMessage()).append("\n");
        sb.append("\n}");
        return sb.toString();
    }
}
