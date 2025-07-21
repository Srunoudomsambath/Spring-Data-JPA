//package kh.edu.istad.mobilebankingapi.service.impl;
//
//import kh.edu.istad.mobilebankingapi.domain.AccountType;
//import kh.edu.istad.mobilebankingapi.dto.AccountTypeResponse;
//import kh.edu.istad.mobilebankingapi.mapper.AccountTypeMapper;
//import kh.edu.istad.mobilebankingapi.repository.AccountTypeRepository;
//import kh.edu.istad.mobilebankingapi.service.AccountTypeService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class AccountTypeServiceImpl implements AccountTypeService {
//    private final AccountTypeRepository accountTypeRepository;
//    private final AccountTypeMapper accountTypeMapper;
//    @Override
//    public List<AccountTypeResponse> findAllAccountTypes() {
//        List<AccountType> accountTypes = accountTypeRepository.findAll();
//        return accountTypes.stream().map(accountTypeMapper::fromAccountType).toList();
//    }
//}
