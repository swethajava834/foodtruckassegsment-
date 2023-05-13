package com.swetha.foodtruckassessment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "User already exists!")
public class UserAlreadyExistsException extends Exception {

    public UserAlreadyExistsException(String message){
        super(message);
    }
}
