package kh.edu.istad.mobilebankingapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateAccountRequest (
        @NotBlank(message = "accountNumber must not be blank")
        String accountNumber,
        @NotBlank(message = "Please enter Currency")
        String accountCurrency,
        @NotNull(message = "Please enter balance")
        BigDecimal balance,
        @NotNull(message = "customerId is required")
        Integer customerId,
        @NotNull(message = "accountTypeId is required")
        Integer accountTypeId
) {
}
