package com.example.election.service;

import com.example.election.dto.OtpVerifyRequest;

import java.util.UUID;

public interface OtpService {
    String generateOtp(UUID userId);
    boolean validateOtp(OtpVerifyRequest request);
}
