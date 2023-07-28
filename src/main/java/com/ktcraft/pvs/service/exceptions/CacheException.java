package com.ktcraft.pvs.service.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class CacheException extends GeneralException {

    public CacheException(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }
}
