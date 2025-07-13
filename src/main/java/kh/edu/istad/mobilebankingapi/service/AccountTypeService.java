package kh.edu.istad.mobilebankingapi.service;

import kh.edu.istad.mobilebankingapi.domain.AccountType;
import kh.edu.istad.mobilebankingapi.dto.AccountTypeResponse;

import java.util.List;

public interface AccountTypeService {
    List<AccountTypeResponse> findAllAccountTypes();
}
