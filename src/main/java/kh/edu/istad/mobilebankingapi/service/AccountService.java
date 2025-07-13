package kh.edu.istad.mobilebankingapi.service;

import kh.edu.istad.mobilebankingapi.domain.Account;
import kh.edu.istad.mobilebankingapi.dto.AccountResponse;
import kh.edu.istad.mobilebankingapi.dto.CreateAccountRequest;

import java.util.List;

public interface AccountService {

    AccountResponse createAccount(CreateAccountRequest createAccountRequest);
    List<AccountResponse> findAllAccounts();

//    Create a new account
//- Find all accounts
//- Find an account by actNo
//- Find accounts by customer
//- Delete an account by actNo
//- Update an account information by actNo
//- Disable an account by actNo (update is_deleted to true)
}
