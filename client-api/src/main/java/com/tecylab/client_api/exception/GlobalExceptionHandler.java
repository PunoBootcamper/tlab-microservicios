package com.tecylab.client_api.exception;

import com.tecylab.client_api.dto.ErrorDTO;
import com.tecylab.client_api.model.OperationLogEntity;
import com.tecylab.client_api.repository.OperationLogRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private final OperationLogRepository operationLogRepository;

    public GlobalExceptionHandler(OperationLogRepository operationLogRepository) {
        this.operationLogRepository = operationLogRepository;
    }

    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleClientNotFoundException(ClientNotFoundException ex) {
        ErrorDTO errorDTO = new ErrorDTO(
                HttpStatus.NOT_FOUND.toString(),
                "Client Not Found",
                ex.getMessage(),
                java.time.LocalDateTime.now(),
                "/clients/{id}"
        );
        operationLogRepository.save(OperationLogEntity.builder()
                        .operation("ERROR")
                        .description(ex.getMessage())
                        .timestamp(java.time.LocalDateTime.now())
                .build());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDTO);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> handleException(Exception ex) {
        ErrorDTO errorDTO = new ErrorDTO(
                HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                "Internal Server Error",
                ex.getMessage(),
                java.time.LocalDateTime.now(),
                "/clients"
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDTO);
    }
}
