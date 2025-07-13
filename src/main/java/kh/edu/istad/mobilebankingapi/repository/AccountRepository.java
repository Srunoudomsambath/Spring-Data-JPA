package kh.edu.istad.mobilebankingapi.repository;

import kh.edu.istad.mobilebankingapi.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account,Integer> {


    boolean existsByAccountNumber(String accountNumber);
    // SELECT * FROM Account WHERE accountNumber = ?
    Optional<Account> findByAccountNumber(String accountNumber);
    List<Account> findByCustomerId(Integer customerId);
}
