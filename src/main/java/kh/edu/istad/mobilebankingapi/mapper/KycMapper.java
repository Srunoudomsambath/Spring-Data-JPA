package kh.edu.istad.mobilebankingapi.mapper;

import kh.edu.istad.mobilebankingapi.domain.KYC;
import kh.edu.istad.mobilebankingapi.dto.CreateKycRequest;
import kh.edu.istad.mobilebankingapi.dto.KycResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface KycMapper {
    KycResponse fromKYC(KYC kyc);
    KYC toKYC(CreateKycRequest request);
}
