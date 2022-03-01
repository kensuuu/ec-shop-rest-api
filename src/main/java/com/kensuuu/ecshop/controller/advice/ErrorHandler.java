package com.kensuuu.ecshop.controller.advice;

import com.kensuuu.ecshop.exception.BadRequestException;
import com.kensuuu.ecshop.exception.InternalServerErrorException;
import com.kensuuu.ecshop.exception.NotFoundException;
import com.kensuuu.ecshop.exception.UnAuthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<?> handleNotFoundException(NotFoundException e) {
        log.error("Error: {}", e.getMessage());
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(UnAuthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<?> handleUnAuthorizedException(NotFoundException e) {
        log.error("Error: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleBadRequestException(BadRequestException e) {
        log.error("Error: {}", e.getMessage());
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(InternalServerErrorException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<?> handleInternalServerErrorException(InternalServerErrorException e) {
        log.error("Error: {}", e.getMessage());
        return ResponseEntity.internalServerError().build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    public ResponseEntity<?> handleException(Exception e) {
        log.error("Error: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
    }
}
