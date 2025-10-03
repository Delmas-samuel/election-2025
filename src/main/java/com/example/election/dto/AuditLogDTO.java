package com.example.election.dto;

import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class AuditLogDTO {
    private Long id;
    private UUID userId;
    private String message;
    private String details;
    private String ip;
    private String location;
    private Instant createdAt;
}
