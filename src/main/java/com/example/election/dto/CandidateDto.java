package com.example.election.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class CandidateDto {
    private UUID id;
    private String firstName;
    private String lastName;
    private String partyName;
}
