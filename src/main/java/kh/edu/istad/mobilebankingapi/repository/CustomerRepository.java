package kh.edu.istad.mobilebankingapi.repository;

import kh.edu.istad.mobilebankingapi.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

//entity, primaryKey
public interface CustomerRepository extends JpaRepository<Customer,Integer>{

    // hibernate generate check validation
    boolean existsByEmail(String email);
    // Derived Query method
    boolean existsByPhoneNumber(String phoneNumber);

    // JPQL
    // Customer c = new Customer():
    @Modifying
    @Query(value = """
                UPDATE Customer c SET c.isDeleted = TRUE 
                                WHERE c.phoneNumber = :phoneNumber
                """)
    void disabledByPhoneNumber(String phoneNumber);

    @Query(value = """
               SELECT EXISTS (SELECT c.id
                       FROM Customer c 
                               WHERE c.phoneNumber = ?1)
              """)
    boolean isExistsByPhoneNumber(String phoneNumber);
    // SELECT * FROM customers WHERE phoneNumber = ?
    Optional<Customer> findByPhoneNumber(String phoneNumber);

    List<Customer> findAllByIsDeletedFalse();
}
