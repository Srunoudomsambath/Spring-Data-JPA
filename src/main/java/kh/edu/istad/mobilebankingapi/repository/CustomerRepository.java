package kh.edu.istad.mobilebankingapi.repository;

import kh.edu.istad.mobilebankingapi.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

//entity, primarykey
public interface CustomerRepository extends JpaRepository<Customer,Integer>{

    // hibernate generate check validation
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);

    // SELECT * FROM customers WHERE phoneNumber = ?
    Optional<Customer> findByPhoneNumber(String phoneNumber);
}
