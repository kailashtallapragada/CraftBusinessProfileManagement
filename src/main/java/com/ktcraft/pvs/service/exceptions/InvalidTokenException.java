package com.ktcraft.pvs.service.exceptions;

import com.ktcraft.pvs.constants.ErrorConstants;
import org.springframework.http.HttpStatus;

public class InvalidTokenException extends GeneralException {

    public InvalidTokenException() {
        super(HttpStatus.FORBIDDEN, ErrorConstants.InvalidAuthToken.getMessage());
    }
}
