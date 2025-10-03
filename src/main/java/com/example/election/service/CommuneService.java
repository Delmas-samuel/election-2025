package com.example.election.service;

import com.example.election.dto.CommuneDTO;
import com.example.election.model.Commune;
import com.example.election.model.Department;
import com.example.election.model.Office;
import com.example.election.repository.CommuneRepository;
import com.example.election.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommuneService {

    private final CommuneRepository communeRepository;
    private final DepartmentRepository departmentRepository;

    public List<CommuneDTO> getAllCommunes() {
        return communeRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public CommuneDTO getById(UUID id) {
        Commune commune = communeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Commune introuvable"));
        return toDTO(commune);
    }

    public CommuneDTO create(CommuneDTO dto) {
        Department department = departmentRepository.findById(dto.getDepartmentId())
                .orElseThrow(() -> new IllegalArgumentException("Département introuvable"));

        Commune commune = Commune.builder()
                .name(dto.getName())
                .department(department)
                .build();

        Commune saved = communeRepository.save(commune);
        return toDTO(saved);
    }

    public CommuneDTO update(UUID id, CommuneDTO dto) {
        Commune commune = communeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Commune introuvable"));

        if (dto.getName() != null) {
            commune.setName(dto.getName());
        }

        if (dto.getDepartmentId() != null) {
            Department department = departmentRepository.findById(dto.getDepartmentId())
                    .orElseThrow(() -> new IllegalArgumentException("Département introuvable"));
            commune.setDepartment(department);
        }

        Commune updated = communeRepository.save(commune);
        return toDTO(updated);
    }

    public void delete(UUID id) {
        communeRepository.deleteById(id);
    }

    private CommuneDTO toDTO(Commune commune) {
        CommuneDTO dto = new CommuneDTO();
        dto.setId(commune.getId());
        dto.setName(commune.getName());
        dto.setDepartmentId(
                commune.getDepartment() != null ? commune.getDepartment().getId() : null
        );
        if (commune.getOffices() != null) {
            dto.setOfficeIds(
                    commune.getOffices().stream()
                            .map(Office::getId)
                            .collect(Collectors.toSet())
            );
        }
        return dto;
    }
}
