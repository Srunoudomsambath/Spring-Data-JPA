package kh.edu.istad.mobilebankingapi.dto;

import jakarta.validation.constraints.NotBlank;
public record CreateCustomerRequest(
        @NotBlank(message = "Full name is required")
        String fullName,
        @NotBlank(message = "Gender is required")
        String gender,
        String email,
        String phoneNumber,
        String remark
) {
}
