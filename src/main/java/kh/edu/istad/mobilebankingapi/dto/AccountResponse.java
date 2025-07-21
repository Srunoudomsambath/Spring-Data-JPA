package kh.edu.istad.mobilebankingapi.dto;

import jakarta.persistence.Column;

import java.math.BigDecimal;

public record AccountResponse (
        String actNo,
        String actName,
        String actCurrency,
        BigDecimal balance,
        String accountType,
        Boolean isHide

){

}