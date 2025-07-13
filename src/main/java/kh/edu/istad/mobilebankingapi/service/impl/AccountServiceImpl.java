package kh.edu.istad.mobilebankingapi.service.impl;


import kh.edu.istad.mobilebankingapi.domain.Account;
import kh.edu.istad.mobilebankingapi.domain.AccountType;
import kh.edu.istad.mobilebankingapi.domain.Customer;
import kh.edu.istad.mobilebankingapi.dto.AccountResponse;
import kh.edu.istad.mobilebankingapi.dto.CreateAccountRequest;
import kh.edu.istad.mobilebankingapi.dto.UpdateAccountRequest;
import kh.edu.istad.mobilebankingapi.mapper.AccountMapper;
import kh.edu.istad.mobilebankingapi.repository.AccountRepository;
import kh.edu.istad.mobilebankingapi.repository.AccountTypeRepository;
import kh.edu.istad.mobilebankingapi.repository.CustomerRepository;
import kh.edu.istad.mobilebankingapi.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.hibernate.sql.Update;
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

    @Override
    public AccountResponse findAccountByAccountNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .map(accountMapper::fromAccount)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));
    }

    @Override
    public List<AccountResponse> findAccountByCustomerId(Integer customerId) {
        return accountRepository.findByCustomerId(customerId)
                .stream().map(accountMapper::fromAccount).toList();
    }

    @Override
    public AccountResponse updateAccountByAccountNumber(String accountNumber, UpdateAccountRequest updateAccountRequest) {

        //Validation AccountNumber must not be blank
        if(updateAccountRequest.accountNumber() != null ){
            String actNumber = updateAccountRequest.accountNumber().trim();
            if(actNumber.isBlank()){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account number must not be blank");
            }
        }
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));
        accountMapper.toAccountPartially(updateAccountRequest, account);
        account = accountRepository.save(account);
        return accountMapper.fromAccount(account);
    }

    @Override
    public void deleteAccountByAccountNumber(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));
        accountRepository.delete(account);
    }
}
