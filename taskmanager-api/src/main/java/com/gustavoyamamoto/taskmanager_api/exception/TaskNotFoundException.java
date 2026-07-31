package com.gustavoyamamoto.taskmanager_api.exception;

public class TaskNotFoundException extends RuntimeException{
    public TaskNotFoundException(String message){
        super(message);
    }
}
