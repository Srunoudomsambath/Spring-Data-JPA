package kh.edu.istad.mobilebankingapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kh.edu.istad.mobilebankingapi.util.CurrencyUtil;

import java.math.BigDecimal;

public record CreateAccountRequest (
        String actNo,
        String actName,
        CurrencyUtil actCurrency,
        BigDecimal balance,
        String phoneNumber,
        String accountType
) {
}
