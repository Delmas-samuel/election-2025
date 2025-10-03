package com.example.election.dto;

import lombok.Data;
import java.time.Instant;
import java.util.UUID;

@Data
public class PvFileDTO {
    private UUID id;
    private String filename;
    private String contentType;
    private Long sizeBytes;
    private Instant uploadedAt;
    private UUID resultId;
    private UUID userId;
    private UUID officeId;
}
