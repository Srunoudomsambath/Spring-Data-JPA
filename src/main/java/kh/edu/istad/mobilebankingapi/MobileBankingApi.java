package kh.edu.istad.mobilebankingapi;

import kh.edu.istad.mobilebankingapi.domain.Customer;
import kh.edu.istad.mobilebankingapi.domain.KYC;
import kh.edu.istad.mobilebankingapi.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class MobileBankingApi implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(MobileBankingApi.class, args);
    }

    private final CustomerRepository customerRepository;
    @Override
    public void run(String... args) throws Exception {



        KYC kyc = new KYC();
        Customer customer = new Customer();

        kyc.setNationalCardId("9999900000");
        kyc.setIsVerified(false);
        kyc.setIsDeleted(false);
        kyc.setCustomer(customer);

        customer.setFullName("Srun Oudomsambath");
        customer.setGender("M");
        customer.setEmail("it.sambath@gmail.com");
        customer.setPhoneNumber("1234567890");
        customer.setRemark("STUDENT");
        customer.setKyc(kyc);
        customer.setIsDeleted(false);

        customerRepository.save(customer);
    }
}
