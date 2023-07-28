package com.ktcraft.pvs.service.exceptions.handlers;

import com.ktcraft.pvs.service.exceptions.GeneralException;
import com.ktcraft.pvs.service.exceptions.dto.ExceptionDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GeneralExceptionHandler {

    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<ExceptionDto> handleInvalidRequestException (
            GeneralException ex) {
        return ResponseEntity.status(ex.getHttpStatus())
                .body(new ExceptionDto(ex.getHttpStatus().value(), ex.getMessage()));
    }
}
