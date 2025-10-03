package com.example.election.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class VoteDto {
    private UUID id;
    private UUID candidateId;
    private UUID officeId;
    private int maleVoters;
    private int femaleVoters;
}
