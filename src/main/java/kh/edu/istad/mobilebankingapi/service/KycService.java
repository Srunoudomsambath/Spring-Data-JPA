package kh.edu.istad.mobilebankingapi.service;

import kh.edu.istad.mobilebankingapi.dto.KycResponse;

import java.util.List;

public interface KycService {
    KycResponse verifyKyc(Integer customerId);
}
