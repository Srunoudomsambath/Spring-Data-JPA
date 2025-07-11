package kh.edu.istad.mobilebankingapi.exception;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

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
}
