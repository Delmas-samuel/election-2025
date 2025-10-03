package com.example.election.dto;

import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private String numero;
    private String email;
    private Long roleId;
    private UUID officeId;
}
