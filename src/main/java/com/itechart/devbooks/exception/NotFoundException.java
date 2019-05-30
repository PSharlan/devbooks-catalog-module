package com.itechart.devbooks.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class NotFoundException extends RuntimeException {

    @Getter
    private HttpStatus status;

    public NotFoundException(String message){
        super(message);
        this.status = HttpStatus.NOT_FOUND;
    }
}
