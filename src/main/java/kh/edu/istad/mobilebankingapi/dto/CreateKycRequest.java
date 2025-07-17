package kh.edu.istad.mobilebankingapi.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateKycRequest (
        @NotBlank
        String nationalCardId
){
}
