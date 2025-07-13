package kh.edu.istad.mobilebankingapi.controller;


import jakarta.validation.Valid;
import kh.edu.istad.mobilebankingapi.domain.Account;
import kh.edu.istad.mobilebankingapi.dto.AccountResponse;
import kh.edu.istad.mobilebankingapi.dto.CreateAccountRequest;
import kh.edu.istad.mobilebankingapi.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    @PostMapping
    public AccountResponse createAccount(@Valid @RequestBody CreateAccountRequest createAccountRequest) {
        return accountService.createAccount(createAccountRequest);

    }
    @GetMapping
    public List<AccountResponse> findAllAccounts() {
        return accountService.findAllAccounts();
    }

}
