package kh.edu.istad.mobilebankingapi.dto;

import lombok.Builder;

@Builder
public record CustomerResponse (
        String fullName,
        String gender,
        String remark,
        String email
){
}
