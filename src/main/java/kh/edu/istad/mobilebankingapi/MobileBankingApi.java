package kh.edu.istad.mobilebankingapi;

import kh.edu.istad.mobilebankingapi.domain.AccountType;
import kh.edu.istad.mobilebankingapi.domain.Customer;
import kh.edu.istad.mobilebankingapi.domain.KYC;
import kh.edu.istad.mobilebankingapi.domain.Segment;
import kh.edu.istad.mobilebankingapi.repository.AccountTypeRepository;
import kh.edu.istad.mobilebankingapi.repository.CustomerRepository;
import kh.edu.istad.mobilebankingapi.repository.KYCRepository;
import kh.edu.istad.mobilebankingapi.repository.SegmentRepository;
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
    private final AccountTypeRepository accountTypeRepository;
    private final SegmentRepository  segmentRepository;
    @Override
    public void run(String... args) throws Exception {

        Segment segment = new Segment();
        segment.setName("Gold");
        segment.setIsDeleted(false);
        segment.setDescription("High customers");

        Segment segment1 = new Segment();
        segment1.setName("Silver");
        segment1.setIsDeleted(false);
        segment1.setDescription("Medium customers");

        Segment segment2 = new Segment();
        segment2.setName("Regular");
        segment2.setIsDeleted(false);
        segment2.setDescription("Low customers");
        segmentRepository.save(segment);
        segmentRepository.save(segment1);
        segmentRepository.save(segment2);
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
        customer.setSegment(segment);

        customer.setKyc(kyc);
        customer.setIsDeleted(false);

        customerRepository.save(customer);

        AccountType accountType  = new AccountType();
        accountType.setName("Saving");

        AccountType accountType1 = new AccountType();
        accountType1.setName("Current");

        accountTypeRepository.save(accountType);
        accountTypeRepository.save(accountType1);



    }
}
