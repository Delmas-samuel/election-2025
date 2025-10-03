package com.example.election.dto;

import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResultDto {
    private UUID officeId;
    private UUID submittedById;
    private String votesJson;
    private byte[] signature;
}
