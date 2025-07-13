package kh.edu.istad.mobilebankingapi.dto;

import java.math.BigDecimal;

public record UpdateAccountRequest(
    String accountNumber,
    String accountCurrency,
    BigDecimal balance

){
}
