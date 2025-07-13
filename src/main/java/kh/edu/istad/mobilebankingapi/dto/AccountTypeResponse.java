package kh.edu.istad.mobilebankingapi.dto;

import java.util.List;

public record AccountTypeResponse (
        Integer id,
        String name,
        List<AccountResponse> accounts
){
}
