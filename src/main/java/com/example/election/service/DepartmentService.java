package com.example.election.service;



import com.example.election.model.Department;
import com.example.election.model.Region;
import com.example.election.repository.DepartmentRepository;
import com.example.election.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final RegionRepository regionRepository;

    public Department createDepartment(String name, Long regionId) {
        Region region = regionRepository.findById(regionId)
                .orElseThrow(() -> new IllegalArgumentException("Région introuvable"));

        Department department = Department.builder()
                .name(name)
                .region(region)
                .build();

        return departmentRepository.save(department);
    }

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public Department getDepartmentById(Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Département introuvable"));
    }
}
