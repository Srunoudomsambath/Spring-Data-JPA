package kh.edu.istad.mobilebankingapi.dto;

import jakarta.validation.constraints.NotBlank;
import kh.edu.istad.mobilebankingapi.domain.Segment;

public record CreateCustomerRequest(
        @NotBlank(message = "Full name is required")
        String fullName,
        @NotBlank(message = "Gender is required")
        String gender,
        @NotBlank(message = "Email is required")
        String email,
        @NotBlank(message = "PhoneNumber is required")
        String phoneNumber,
        @NotBlank(message = "Remark is required")
        String remark,
        @NotBlank(message = "NationalCardId is required")
        CreateKycRequest createKycRequest,
        @NotBlank(message = "Segment Id is required")
        Integer segmentId

) {
}
