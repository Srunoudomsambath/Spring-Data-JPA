package kh.edu.istad.mobilebankingapi.dto;

public record KycResponse (
        String nationalCardId,
        Boolean isVerified
){
}
