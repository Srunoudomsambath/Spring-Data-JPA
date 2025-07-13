package kh.edu.istad.mobilebankingapi.service.impl;


import kh.edu.istad.mobilebankingapi.domain.Account;
import kh.edu.istad.mobilebankingapi.domain.AccountType;
import kh.edu.istad.mobilebankingapi.domain.Customer;
import kh.edu.istad.mobilebankingapi.dto.AccountResponse;
import kh.edu.istad.mobilebankingapi.dto.CreateAccountRequest;
import kh.edu.istad.mobilebankingapi.mapper.AccountMapper;
import kh.edu.istad.mobilebankingapi.repository.AccountRepository;
import kh.edu.istad.mobilebankingapi.repository.AccountTypeRepository;
import kh.edu.istad.mobilebankingapi.repository.CustomerRepository;
import kh.edu.istad.mobilebankingapi.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final CustomerRepository customerRepository;
    private final AccountTypeRepository accountTypeRepository;


    @Override
    public AccountResponse createAccount(CreateAccountRequest createAccountRequest) {

        if(accountRepository.existsByAccountNumber(createAccountRequest.accountNumber())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Account already exists");
        }

        Account account = accountMapper.toAccount(createAccountRequest);

        // TODO Set required foreign key relationships

        Customer customer = customerRepository.findById(createAccountRequest.customerId())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));

        AccountType accountType = accountTypeRepository.findById(createAccountRequest.accountTypeId())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "AccountType not found"));

        account.setCustomer(customer);
        account.setAccountType(accountType);
        accountRepository.save(account);

        return accountMapper.fromAccount(account);
    }

    @Override
    public List<AccountResponse> findAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        return accounts.stream().map(accountMapper::fromAccount).toList();
    }
}
