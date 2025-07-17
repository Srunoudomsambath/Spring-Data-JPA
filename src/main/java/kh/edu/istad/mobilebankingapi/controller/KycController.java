package kh.edu.istad.mobilebankingapi.controller;

import kh.edu.istad.mobilebankingapi.dto.KycResponse;
import kh.edu.istad.mobilebankingapi.service.KycService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/v1/kyc")
@RestController
@RequiredArgsConstructor
public class KycController {
    private final KycService kycService;

    @PutMapping("{customerId}")
    public KycResponse verifyKyc(@PathVariable Integer customerId ) {
        return kycService.verifyKyc(customerId);
    }
}
