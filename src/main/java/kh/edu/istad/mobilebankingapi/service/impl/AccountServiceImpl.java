package kh.edu.istad.mobilebankingapi.service.impl;


import kh.edu.istad.mobilebankingapi.domain.Account;
import kh.edu.istad.mobilebankingapi.domain.AccountType;
import kh.edu.istad.mobilebankingapi.domain.Customer;
import kh.edu.istad.mobilebankingapi.domain.Segment;
import kh.edu.istad.mobilebankingapi.dto.AccountResponse;
import kh.edu.istad.mobilebankingapi.dto.CreateAccountRequest;
import kh.edu.istad.mobilebankingapi.dto.UpdateAccountRequest;
import kh.edu.istad.mobilebankingapi.mapper.AccountMapper;
import kh.edu.istad.mobilebankingapi.repository.AccountRepository;
import kh.edu.istad.mobilebankingapi.repository.AccountTypeRepository;
import kh.edu.istad.mobilebankingapi.repository.CustomerRepository;
import kh.edu.istad.mobilebankingapi.service.AccountService;
import kh.edu.istad.mobilebankingapi.util.CurrencyUtil;
import lombok.RequiredArgsConstructor;
import org.hibernate.sql.Update;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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

        // TODO Set required foreign key relationships

        Customer customer = customerRepository.findByPhoneNumber(createAccountRequest.phoneNumber())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));

        AccountType accountType = accountTypeRepository.findByType(createAccountRequest.accountType())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "AccountType not found"));

        Account account = accountMapper.toAccount(createAccountRequest);
        account.setCustomer(customer);
        account.setAccountType(accountType);

        if (account.getActNo().isBlank()) { // Auto generate
            String actNo;
            do {
                actNo = String.format("%09d", new Random().nextInt(1_000_000_000)); // Max: 999,999,999
            } while (accountRepository.existsByActNo(actNo));
            account.setActNo(actNo);
        } else { // From DTO, check validation actNo
            if (accountRepository.existsByActNo(account.getActNo())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT,
                        "Account number already exists");
            }
        }
        account.setIsHide(false);
        account.setIsDeleted(false);
        account.setActCurrency(createAccountRequest.actCurrency().name());

        if (account.getCustomer().getSegment().getSegment().equals("REGULAR")) {
            account.setOverLimit(BigDecimal.valueOf(5000));
        } else if (account.getCustomer().getSegment().getSegment().equals("SILVER")) {
            account.setOverLimit(BigDecimal.valueOf(50000));
        } else {
            account.setOverLimit(BigDecimal.valueOf(100000));
        }

        // Validate balance
        switch (createAccountRequest.actCurrency()) {
            case CurrencyUtil.DOLLAR -> {
                if (createAccountRequest.balance().compareTo(BigDecimal.TEN) < 0) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Balance must be greater than 10 dollars");
                }
            }
            case CurrencyUtil.RIEL -> {
                if (createAccountRequest.balance().compareTo(BigDecimal.valueOf(40000)) < 0) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Balance must be greater than 40000 riels");
                }
            }
            default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Currency is not available");
        }


         account = accountRepository.save(account);

        return accountMapper.fromAccount(account);
    }

    @Override
    public List<AccountResponse> findAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        return accounts.stream().map(accountMapper::fromAccount).toList();
    }

    @Override
    public AccountResponse findAccountByAccountNumber(String accountNumber) {
        return accountRepository.findByActNo(accountNumber)
                .map(accountMapper::fromAccount)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));
    }

    @Override
    public List<AccountResponse> findAccountByCustomerId(Integer customerId) {
        return accountRepository.findByCustomerId(customerId)
                .stream().map(accountMapper::fromAccount).toList();
    }

    @Override
    public AccountResponse updateAccountByAccountNumber(String actNo, UpdateAccountRequest updateAccountRequest) {

        //Validation AccountNumber must not be blank
        if(updateAccountRequest.actNo() != null ){
            String actNumber = updateAccountRequest.actNo().trim();
            if(actNumber.isBlank()){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account number must not be blank");
            }
        }
        Account account = accountRepository.findByActNo(actNo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));
        accountMapper.toAccountPartially(updateAccountRequest, account);
        account = accountRepository.save(account);
        return accountMapper.fromAccount(account);
    }

    @Override
    public void deleteAccountByAccountNumber(String actNo) {
        Account account = accountRepository.findByActNo(actNo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));
        accountRepository.delete(account);
    }

    @Override
    public void disabledAccountByAccountNumber(String accountNumber) {
        int affected = accountRepository.disableAccountByAccountNumber(accountNumber);
        if(affected == 0){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
        }
    }
}
