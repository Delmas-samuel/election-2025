package com.example.election.dto;


import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
public class CommuneDTO {
    private UUID id;
    private String name;
    private Long departmentId;
    private Set<UUID> officeIds;
}
