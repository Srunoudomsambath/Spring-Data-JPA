package kh.edu.istad.mobilebankingapi.service.impl;

import kh.edu.istad.mobilebankingapi.domain.KYC;
import kh.edu.istad.mobilebankingapi.dto.KycResponse;
import kh.edu.istad.mobilebankingapi.mapper.KycMapper;
import kh.edu.istad.mobilebankingapi.repository.KYCRepository;
import kh.edu.istad.mobilebankingapi.service.KycService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KycServiceImpl implements KycService {
    private final KycMapper kycMapper;
    private final KYCRepository  kycRepository;

    @Override
    public KycResponse verifyKyc(Integer customerId) {
        KYC kyc = kycRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("KYC not found"));
        if (kyc.getIsVerified()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "KYC is already verified");
        }
        kyc.setIsVerified(true);
        return kycMapper.fromKYC(kycRepository.save(kyc));
    }
}
