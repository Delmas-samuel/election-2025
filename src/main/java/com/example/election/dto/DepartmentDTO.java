package com.example.election.dto;



import lombok.Data;

import java.util.Set;

@Data
public class DepartmentDTO {
    private Long id;
    private String name;
    private Long regionId;
    private Set<String> communeNames;
}
