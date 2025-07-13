package kh.edu.istad.mobilebankingapi.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ServiceException {

    //    public Map<String,?> handleServiceException(ResponseStatusException e){
//
//    }
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<?> handleException(ResponseStatusException e){
        ErrorResponse<String > error = ErrorResponse.<String>builder()
                .message("Business Logic Error")
                .status(e.getStatusCode().value())
                .timestamp(LocalDateTime.now())
                .detail(e.getReason())
                .build();

        return ResponseEntity.status(e.getStatusCode()).body(error);
    }
    // Handle validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                fieldErrors.put(error.getField(), error.getDefaultMessage())
        );

        ErrorResponse<Map<String, String>> error;
        error = ErrorResponse.<Map<String, String>>builder()
                .message("Validation Failed")
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now())
                .detail(fieldErrors)
                .build();

        return ResponseEntity.badRequest().body(error);
    }
}
