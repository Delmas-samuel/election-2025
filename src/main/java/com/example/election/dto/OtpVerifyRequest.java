package com.example.election.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class OtpVerifyRequest {
    private UUID userId;
    private String code;
}
