package com.luke.filmdb.application.resource;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessage {

    private Integer code;
    private String message;

    @Override
    public int hashCode() {
        int result = code != null ? code.hashCode() : 0;
        result = 31 * result + (message != null ? message.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ErrorMessage)) return false;

        ErrorMessage that = (ErrorMessage) o;

        if (!getCode().equals(that.getCode())) return false;
        return getMessage().equals(that.getMessage());
    }

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
