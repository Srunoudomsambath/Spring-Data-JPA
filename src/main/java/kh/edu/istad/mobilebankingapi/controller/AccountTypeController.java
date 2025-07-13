package kh.edu.istad.mobilebankingapi.controller;

import kh.edu.istad.mobilebankingapi.domain.AccountType;
import kh.edu.istad.mobilebankingapi.dto.AccountTypeResponse;
import kh.edu.istad.mobilebankingapi.repository.AccountTypeRepository;
import kh.edu.istad.mobilebankingapi.service.AccountTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/accountType")
@RequiredArgsConstructor
public class AccountTypeController {
    private final AccountTypeService accountTypeService;

    @GetMapping
    public List<AccountTypeResponse> getAllAccountTypes(){
        return accountTypeService.findAllAccountTypes();
    }
}
