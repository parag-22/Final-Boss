package com.ofds.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ofds.exception.DataNotFoundException;
import com.ofds.exception.NoDataFoundException;
import com.ofds.exception.RecordAlreadyFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<String> DataNotFoundExceptionInfo (DataNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

//    @ExceptionHandler(IllegalArgumentException.class)
//    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException ex) {
//        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
//    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @ExceptionHandler(RecordAlreadyFoundException.class)
    public ResponseEntity<String> handleRecordAlreadyFound(RecordAlreadyFoundException ex)
    {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT); // 409
    }
    
    @ExceptionHandler(NoDataFoundException.class)
    public ResponseEntity<String> handleNoDataFound(NoDataFoundException ex) 
    {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND); // 404
    }

}