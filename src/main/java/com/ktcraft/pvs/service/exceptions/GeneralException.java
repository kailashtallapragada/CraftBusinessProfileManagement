package com.ktcraft.pvs.service.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class GeneralException extends RuntimeException {
    private final HttpStatus httpStatus;

    public GeneralException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
