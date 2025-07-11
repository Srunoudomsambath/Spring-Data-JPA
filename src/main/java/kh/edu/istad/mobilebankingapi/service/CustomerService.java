package kh.edu.istad.mobilebankingapi.service;

import kh.edu.istad.mobilebankingapi.dto.CreateCustomerRequest;
import kh.edu.istad.mobilebankingapi.dto.CustomerResponse;
import kh.edu.istad.mobilebankingapi.dto.UpdateCustomerRequest;
import kh.edu.istad.mobilebankingapi.repository.CustomerRepository;

import java.util.List;

public interface CustomerService {


    CustomerResponse createNew(CreateCustomerRequest createCustomerRequest);
    List<CustomerResponse> findAll();
    CustomerResponse findByPhoneNumber(String phoneNumber);
    CustomerResponse updateByPhoneNumber(String phoneNumber, UpdateCustomerRequest updateCustomerRequest);
    void deleteByPhoneNumber(String phoneNumber);
}
