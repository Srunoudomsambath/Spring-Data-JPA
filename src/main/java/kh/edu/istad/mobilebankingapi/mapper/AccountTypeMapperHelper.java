package kh.edu.istad.mobilebankingapi.mapper;

import kh.edu.istad.mobilebankingapi.domain.AccountType;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
public class AccountTypeMapperHelper {

    @Named("mapAccountTypeToString")
    public String mapAccountTypeToString(AccountType accountType) {
        return accountType != null ? accountType.getType() : null;
    }
}
