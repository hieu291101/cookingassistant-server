package com.lth.cookingassistant.exception.handler;

import com.lth.cookingassistant.exception.CANotFoundException;
import com.lth.cookingassistant.model.ErrorType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import java.sql.Date;

@RestControllerAdvice
public class CAErrorHandler {
    /**
     * In case of InvoiceNotFoundException is thrown
     * from any controller method, this logic gets
     * executed which behaves like re-usable and
     * clear code (Code Modularity)
     * @param cfe
     * @return ResponseEntity
     */
    @ExceptionHandler(CANotFoundException.class)
    public ResponseEntity<ErrorType> handleNotFound(CANotFoundException cfe){
        return new ResponseEntity<>(
                new ErrorType(new Date(System.currentTimeMillis()).toString(),
                        "404- NOT FOUND",
                        cfe.getMessage()),
                HttpStatus.NOT_FOUND);
    }
}
