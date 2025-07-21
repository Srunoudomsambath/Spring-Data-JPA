package kh.edu.istad.mobilebankingapi.dto;

import java.math.BigDecimal;

public record UpdateAccountRequest(
    String actNo,
    String actCurrency,
    BigDecimal balance

){
}
