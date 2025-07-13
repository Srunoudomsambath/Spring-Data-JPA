package kh.edu.istad.mobilebankingapi.repository;

import kh.edu.istad.mobilebankingapi.domain.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountTypeRepository extends JpaRepository<AccountType,Integer> {

}
