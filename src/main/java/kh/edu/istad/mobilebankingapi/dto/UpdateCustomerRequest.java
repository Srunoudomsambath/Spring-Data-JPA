package kh.edu.istad.mobilebankingapi.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

//Patch no validation meaning user can update any data
public record UpdateCustomerRequest(
        String fullName,
        String gender,
        String remark
) {
}
