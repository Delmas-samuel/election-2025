package com.example.election.controller;

import com.example.election.dto.OtpVerifyRequest;
import com.example.election.service.OtpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/otp")
@RequiredArgsConstructor
public class OtpCodeController {
    private final OtpService otpService;

    @PostMapping("/generate")
    public ResponseEntity<String> generate(@RequestParam UUID userId) {
        return ResponseEntity.ok(otpService.generateOtp(userId));
    }

    @PostMapping("/validate")
    public ResponseEntity<Boolean> validate(@RequestBody OtpVerifyRequest req) {
        return ResponseEntity.ok(otpService.validateOtp(req));
    }
}
