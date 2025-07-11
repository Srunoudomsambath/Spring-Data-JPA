package kh.edu.istad.mobilebankingapi.service;

import kh.edu.istad.mobilebankingapi.domain.Customer;
import kh.edu.istad.mobilebankingapi.dto.CreateCustomerRequest;
import kh.edu.istad.mobilebankingapi.dto.CustomerResponse;
import kh.edu.istad.mobilebankingapi.dto.UpdateCustomerRequest;
import kh.edu.istad.mobilebankingapi.mapper.CustomerMapper;
import kh.edu.istad.mobilebankingapi.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService{
    private final  CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

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
//
//        Customer customer = new Customer();
//        customer.setFullName(createCustomerRequest.fullName());
//        customer.setEmail(createCustomerRequest.email());
//        customer.setPhoneNumber(createCustomerRequest.phoneNumber());
//        customer.setGender(createCustomerRequest.gender());
//        customer.setRemark(createCustomerRequest.remark());
        //         ->
        Customer customer = customerMapper.toCustomer(createCustomerRequest);
        customer.setIsDeleted(false);
        customer.setAccounts(new ArrayList<>());



        log.info("customer before save : {}", customer);
        //it generate new id
        customer = customerRepository.save(customer);

        log.info("customer after save : {}", customer.getId());

//        return CustomerResponse.builder()
//                .fullName(customer.getFullName())
//                .gender(customer.getGender())
//                .remark(customer.getRemark())
//                .build();
        return customerMapper.fromCustomer(customer);
    }

        @Override
        public List<CustomerResponse> findAll() {
        List<Customer> customers = customerRepository.findAll();
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

        customer = customerRepository.save(customer);

        return customerMapper.fromCustomer(customer);
    }


    @Override
    public void deleteByPhoneNumber(String phoneNumber) {
        Customer customer = customerRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
        customerRepository.delete(customer);
    }
}
