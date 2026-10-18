package com.tecylab.product_api.exception;

import com.tecylab.product_api.dto.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ErrorDTO handleProductNotFoundException(ProductNotFoundException ex) {
       return ErrorDTO.builder()
                .status(HttpStatus.NOT_FOUND.toString())
                .title("Product Not Found")
                .detail(ex.getMessage())
                .timestamp(java.time.LocalDateTime.now())
                .path("/products/{id}")
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(Exception.class)
    public ErrorDTO handleException(Exception ex) {
        return ErrorDTO.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.toString())
                .title("Internal Server Error")
                .detail(ex.getMessage())
                .timestamp(java.time.LocalDateTime.now())
                .path("/products")
                .build();
    }
}
