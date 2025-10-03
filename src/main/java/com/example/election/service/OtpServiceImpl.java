package com.example.election.service;

import com.example.election.dto.OtpVerifyRequest;
import com.example.election.model.OtpCode;
import com.example.election.model.User;
import com.example.election.repository.OtpCodeRepository;
import com.example.election.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OtpServiceImpl implements OtpService {

    private final OtpCodeRepository otpCodeRepository;
    private final UserRepository userRepository;

    @Override
    public String generateOtp(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String code = String.valueOf((int) (Math.random() * 900000 + 100000));

        OtpCode otp = OtpCode.builder()
                .user(user)
                .otpHash(code)
                .expiresAt(Instant.now().plusSeconds(300))
                .attempts(0)
                .build();

        otpCodeRepository.save(otp);

        // On doit  brancher un service SMS/email
        return code;
    }

    @Override
    public boolean validateOtp(OtpVerifyRequest request) {
        OtpCode otp = otpCodeRepository.findTopByUser_IdOrderByCreatedAtDesc(request.getUserId())
                .orElseThrow(() -> new RuntimeException("No OTP found"));

        if (otp.getExpiresAt().isBefore(Instant.now())) {
            throw new RuntimeException("OTP expired");
        }

        // increment attempts
        otp.setAttempts(otp.getAttempts() + 1);
        otpCodeRepository.save(otp);
        if (otp.getAttempts() > 3) {
            throw new RuntimeException("OTP blocked after 3 attempts");
        }

        return otp.getOtpHash() != null && otp.getOtpHash().equals(request.getCode());
    }

}
