package kh.edu.istad.mobilebankingapi.dto;

import jakarta.validation.constraints.NotBlank;
import kh.edu.istad.mobilebankingapi.domain.KYC;
import kh.edu.istad.mobilebankingapi.domain.Segment;
import org.hibernate.validator.constraints.Length;

import java.util.List;

//Patch no validation meaning user can update any data
public record UpdateCustomerRequest(
        String fullName,
        String gender,
        String remark
        ) {
}
