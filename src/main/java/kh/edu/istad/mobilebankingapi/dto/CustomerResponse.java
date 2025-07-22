package kh.edu.istad.mobilebankingapi.dto;

import kh.edu.istad.mobilebankingapi.domain.Segment;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record CustomerResponse (
        String fullName,
        String gender,
        String email,
        String phoneNumber,
        LocalDate dob
){
}
