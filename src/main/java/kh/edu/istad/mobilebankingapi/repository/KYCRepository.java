package kh.edu.istad.mobilebankingapi.repository;

import kh.edu.istad.mobilebankingapi.domain.Customer;
import kh.edu.istad.mobilebankingapi.domain.KYC;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface KYCRepository extends JpaRepository<KYC,Integer> {
    boolean existsByNationalCardId(String nationalCardId);
}
