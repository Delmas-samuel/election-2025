package com.example.election.service;

import com.example.election.dto.AuditLogDTO;
import com.example.election.model.AuditLog;
import com.example.election.model.User;
import com.example.election.repository.AuditLogRepository;
import com.example.election.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;
    private final UserRepository userRepository;

    /**
     * Crée un log d’audit avec message, détails, IP et localisation.
     */
    public AuditLog logMessage(UUID userId, String message, String details, String ip, String location) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur introuvable pour l'ID : " + userId));

        AuditLog log = AuditLog.builder()
                .user(user)
                .message(message)
                .details(details)
                .ip(ip)
                .location(location)
                .createdAt(Instant.now())
                .build();

        return auditLogRepository.save(log);
    }

    public List<AuditLogDTO> getAllLogs() {
        return auditLogRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<AuditLogDTO> getLogsByUser(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur introuvable"));
        return auditLogRepository.findByUser(user).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private AuditLogDTO toDto(AuditLog log) {
        AuditLogDTO dto = new AuditLogDTO();
        dto.setId(log.getId());
        dto.setUserId(log.getUser().getId());
        dto.setMessage(log.getMessage());
        dto.setDetails(log.getDetails());
        dto.setIp(log.getIp());
        dto.setLocation(log.getLocation());
        dto.setCreatedAt(log.getCreatedAt());
        return dto;
    }
}
