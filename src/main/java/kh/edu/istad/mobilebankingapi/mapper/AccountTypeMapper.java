package kh.edu.istad.mobilebankingapi.mapper;


import kh.edu.istad.mobilebankingapi.domain.AccountType;
import kh.edu.istad.mobilebankingapi.dto.AccountTypeResponse;
import kh.edu.istad.mobilebankingapi.repository.AccountTypeRepository;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountTypeMapper {
    AccountTypeResponse fromAccountType(AccountType accountType);
}
