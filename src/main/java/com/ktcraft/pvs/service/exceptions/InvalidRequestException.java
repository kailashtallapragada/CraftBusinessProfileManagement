package com.ktcraft.pvs.service.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class InvalidRequestException extends GeneralException {

    public InvalidRequestException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
