package com.gustavoyamamoto.taskmanager_api.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidTaskException.class)
    public ResponseEntity<ErrorResponse> handleInvalidTaskException(InvalidTaskException ex){
        ErrorResponse error = new ErrorResponse(400,"Bad Request", ex.getMessage());
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTaskNotFoundException(TaskNotFoundException ex){
        ErrorResponse error = new ErrorResponse(404,"Not Found", ex.getMessage());
        return ResponseEntity.status(404).body(error);
    }
}
