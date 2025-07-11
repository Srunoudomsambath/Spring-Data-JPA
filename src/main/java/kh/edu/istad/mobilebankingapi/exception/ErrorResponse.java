package kh.edu.istad.mobilebankingapi.exception;

import lombok.Builder;

import java.time.LocalDateTime;
@Builder
public record ErrorResponse<T> (
        String message,
        Integer status,
        LocalDateTime timestamp,
        T detail
) {
}
