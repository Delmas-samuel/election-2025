package com.example.election.dto;

import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OfficeDto {
    private UUID id;
    private String code;

    private UUID communeId;
    private String communeName;

    private Long departmentId;
    private String departmentName;

    private Long regionId;
    private String regionName;

    private int nombreInscrits;
    private int nombreAbsences;
    private int nombreVotants;
    private int bulletinsNuls;
}
