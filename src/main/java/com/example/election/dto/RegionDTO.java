package com.example.election.dto;



import lombok.Data;

import java.util.Set;

@Data
public class RegionDTO {
    private Long id;
    private String name;
    private Set<String> departmentNames;
}
