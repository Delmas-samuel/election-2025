package com.example.election.repository;

import com.example.election.model.OtpCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OtpCodeRepository extends JpaRepository<OtpCode, UUID> {
    Optional<OtpCode> findTopByUser_IdOrderByCreatedAtDesc(UUID userId);
}
