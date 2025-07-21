package kh.edu.istad.mobilebankingapi.dto;

import kh.edu.istad.mobilebankingapi.domain.Segment;
import lombok.Builder;

@Builder
public record CustomerResponse (
        String fullName,
        String gender,
        String email
){
}
