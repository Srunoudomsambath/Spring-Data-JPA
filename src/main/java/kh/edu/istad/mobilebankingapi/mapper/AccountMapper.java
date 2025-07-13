package kh.edu.istad.mobilebankingapi.mapper;


import kh.edu.istad.mobilebankingapi.domain.Account;
import kh.edu.istad.mobilebankingapi.dto.AccountResponse;
import kh.edu.istad.mobilebankingapi.dto.CreateAccountRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountResponse fromAccount(Account account);
    Account toAccount(CreateAccountRequest createAccountRequest);
}
