package com.luke.filmdb.application.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class CustomExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleServerError(Exception e, HttpServletRequest req) {
        String message = e.getLocalizedMessage();
        LOGGER.error(this.createLogMessage(HttpStatus.INTERNAL_SERVER_ERROR, req, message), e);
        return new ResponseEntity<ErrorMessage>(new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), message),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private String createLogMessage(HttpStatus httpStatus, HttpServletRequest req, String message) {
        StringBuilder sb = new StringBuilder();
        sb.append("Error message for request :");
        sb.append(req.getMethod()).append(" : ").append(req.getServletPath());
        sb.append(", with message : ").append(message);
        sb.append(", with status : ").append(httpStatus);
        return sb.toString();
    }
}
