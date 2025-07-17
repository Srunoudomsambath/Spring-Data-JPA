package kh.edu.istad.mobilebankingapi.dto;

import jakarta.persistence.Column;

import java.math.BigDecimal;

public record AccountResponse (
        String accountNumber,
        String accountCurrency,
        BigDecimal balance,
        BigDecimal overLimit

){

}