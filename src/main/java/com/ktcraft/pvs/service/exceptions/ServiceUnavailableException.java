package com.ktcraft.pvs.service.exceptions;

import com.ktcraft.pvs.constants.ErrorConstants;
import org.springframework.http.HttpStatus;

public class ServiceUnavailableException extends GeneralException {

    public ServiceUnavailableException() {
        super(HttpStatus.SERVICE_UNAVAILABLE, ErrorConstants.ServiceUnavailable.getMessage());
    }
}
