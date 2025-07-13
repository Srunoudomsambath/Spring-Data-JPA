package kh.edu.istad.mobilebankingapi.repository;

import jakarta.transaction.Transactional;
import kh.edu.istad.mobilebankingapi.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account,Integer> {


    boolean existsByAccountNumber(String accountNumber);
    // SELECT * FROM Account WHERE accountNumber = ?
    Optional<Account> findByAccountNumber(String accountNumber);
    List<Account> findByCustomerId(Integer customerId);
    // TODO Disabled Account
    @Modifying //  Tells Spring Data it's a modifying query (not select).
    @Transactional // Required for update/delete queries.
    @Query("UPDATE Account a SET a.isDeleted = true WHERE a.accountNumber = :accountNumber")
    int disableAccountByAccountNumber(String accountNumber);
}
