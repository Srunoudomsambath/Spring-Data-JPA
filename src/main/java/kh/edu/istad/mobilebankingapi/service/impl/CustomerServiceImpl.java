package kh.edu.istad.mobilebankingapi.service.impl;

import kh.edu.istad.mobilebankingapi.domain.Customer;
import kh.edu.istad.mobilebankingapi.domain.KYC;
import kh.edu.istad.mobilebankingapi.domain.Segment;
import kh.edu.istad.mobilebankingapi.dto.CreateCustomerRequest;
import kh.edu.istad.mobilebankingapi.dto.CustomerResponse;
import kh.edu.istad.mobilebankingapi.dto.UpdateCustomerRequest;
import kh.edu.istad.mobilebankingapi.mapper.CustomerMapper;
import kh.edu.istad.mobilebankingapi.mapper.KycMapper;
import kh.edu.istad.mobilebankingapi.repository.CustomerRepository;
import kh.edu.istad.mobilebankingapi.repository.KYCRepository;
import kh.edu.istad.mobilebankingapi.repository.SegmentRepository;
import kh.edu.istad.mobilebankingapi.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {
    private final  CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final SegmentRepository  segmentRepository;
    private final KYCRepository kycRepository;
    private final KycMapper kycMapper;

    @Override
    public CustomerResponse createNew(CreateCustomerRequest createCustomerRequest) {

        // Validate email

        if(customerRepository.existsByEmail(createCustomerRequest.email())){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Email already exists");
        }
        // Validate phoneNumber
        if(customerRepository.existsByPhoneNumber(createCustomerRequest.phoneNumber())){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Phone number already exists");
        }
        // Check nationalCardId
        if (kycRepository.existsByNationalCardId(createCustomerRequest.nationalCardId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "National Card ID already exists");
        }

        // Validation customer segment
        Segment segment = segmentRepository.findBySegment(createCustomerRequest.segment())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Segment not found"));

        // Map data from DTO
        Customer customer = customerMapper.toCustomer(createCustomerRequest);

        // Prepare KYC of customer
        KYC kyc = new KYC();
        kyc.setNationalCardId(createCustomerRequest.nationalCardId());
        kyc.setIsVerified(false);
        kyc.setIsDeleted(false);
        kyc.setCustomer(customer);


        log.info("Customer before save: {}", customer.getId());
        customer.setSegment(segment);
        customer = customerRepository.save(customer);

        log.info("Customer before save: {}", customer.getId());
//        return CustomerResponse.builder()
//                .fullName(customer.getFullName())
//                .gender(customer.getGender())
//                .remark(customer.getRemark())
//                .build();
        return customerMapper.fromCustomer(customer);
    }

        @Override
        public List<CustomerResponse> findAll() {
        List<Customer> customers = customerRepository.findAllByIsDeletedFalse();
            return customers.stream()
//                    .map(customer ->CustomerResponse.builder()
//                            .fullName(customer.getFullName())
//                            .gender(customer.getGender())
//                            .remark(customer.getRemark())
//                            .build() ).toList();
                    .map(customerMapper::fromCustomer).toList();
        }

    @Override
    public CustomerResponse findByPhoneNumber(String phoneNumber) {
        return customerRepository
                .findByPhoneNumber(phoneNumber)
                .map(customerMapper::fromCustomer)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
    }


    @Override
    public CustomerResponse updateByPhoneNumber(String phoneNumber, UpdateCustomerRequest updateCustomerRequest) {
        //phoneNumber must be validated
        // 1. Validate fullName only if present
        if (updateCustomerRequest.fullName() != null) {
            String fullName = updateCustomerRequest.fullName().trim();
            if (fullName.isBlank()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "fullName must not be blank if provided");
            }
            // Count words by splitting by spaces
            int wordCount = fullName.split("\\s+").length;
            if (wordCount < 5) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "fullName must have at least 5 words");
            }
        }

        //first we need to find phoneNumber first if it has, we can let user update
        Customer customer = customerRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
        // Second then we need to know if the those 3 attribute has a not (fullName,gender,remark)
//        if (updateCustomerRequest.fullName() != null){
//            customer.setFullName(updateCustomerRequest.fullName());
//        }
        customerMapper.toCustomerPartially(updateCustomerRequest, customer);
        // set managed segment entity to customer
        customer = customerRepository.save(customer);

        return customerMapper.fromCustomer(customer);
    }


    @Override
    public void deleteByPhoneNumber(String phoneNumber) {
        Customer customer = customerRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
        customerRepository.delete(customer);
    }
    @Transactional
    @Override
    public void disabledCustomerByPhoneNumber(String phoneNumber) {
        if(!customerRepository.isExistsByPhoneNumber(phoneNumber)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Customer phone number is not found");
        }
        customerRepository.disabledByPhoneNumber(phoneNumber);
    }
}
