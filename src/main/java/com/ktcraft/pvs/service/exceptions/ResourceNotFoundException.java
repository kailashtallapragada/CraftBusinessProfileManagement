package com.ktcraft.pvs.service.exceptions;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends GeneralException {

    public ResourceNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
