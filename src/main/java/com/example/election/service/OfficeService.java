package com.example.election.service;

import com.example.election.dto.OfficeDto;
import com.example.election.model.Commune;
import com.example.election.model.Department;
import com.example.election.model.Office;
import com.example.election.model.Region;
import com.example.election.repository.CommuneRepository;
import com.example.election.repository.DepartmentRepository;
import com.example.election.repository.OfficeRepository;
import com.example.election.repository.RegionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OfficeService {

    private final OfficeRepository officeRepository;
    private final CommuneRepository communeRepository;
    private final DepartmentRepository departmentRepository;
    private final RegionRepository regionRepository;

    public List<OfficeDto> getAllOffices() {
        return officeRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public OfficeDto getOfficeById(UUID id) {
        Office office = officeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Bureau introuvable avec l'id: " + id));
        return toDto(office);
    }

    public OfficeDto createOffice(OfficeDto dto) {
        validateLogic(dto);

        Commune commune = communeRepository.findById(dto.getCommuneId())
                .orElseThrow(() -> new EntityNotFoundException("Commune introuvable avec l'id: " + dto.getCommuneId()));

        Department department = departmentRepository.findById(dto.getDepartmentId())
                .orElseThrow(() -> new EntityNotFoundException("Département introuvable avec l'id: " + dto.getDepartmentId()));

        Region region = regionRepository.findById(dto.getRegionId())
                .orElseThrow(() -> new EntityNotFoundException("Région introuvable avec l'id: " + dto.getRegionId()));

        Office office = Office.builder()
                .code(dto.getCode())
                .commune(commune)
                .department(department)
                .region(region)
                .nombreInscrits(dto.getNombreInscrits())
                .nombreAbsences(dto.getNombreAbsences())
                .nombreVotants(dto.getNombreVotants())
                .bulletinsNuls(dto.getBulletinsNuls())
                .build();

        office = officeRepository.save(office);
        return toDto(office);
    }

    public OfficeDto updateOffice(UUID id, OfficeDto dto) {
        validateLogic(dto);

        Office office = officeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Bureau introuvable avec l'id: " + id));

        if (dto.getCode() != null) office.setCode(dto.getCode());

        if (dto.getCommuneId() != null) {
            Commune commune = communeRepository.findById(dto.getCommuneId())
                    .orElseThrow(() -> new EntityNotFoundException("Commune introuvable"));
            office.setCommune(commune);
        }

        if (dto.getDepartmentId() != null) {
            Department department = departmentRepository.findById(dto.getDepartmentId())
                    .orElseThrow(() -> new EntityNotFoundException("Département introuvable"));
            office.setDepartment(department);
        }

        if (dto.getRegionId() != null) {
            Region region = regionRepository.findById(dto.getRegionId())
                    .orElseThrow(() -> new EntityNotFoundException("Région introuvable"));
            office.setRegion(region);
        }

        office.setNombreInscrits(dto.getNombreInscrits());
        office.setNombreAbsences(dto.getNombreAbsences());
        office.setNombreVotants(dto.getNombreVotants());
        office.setBulletinsNuls(dto.getBulletinsNuls());

        office = officeRepository.save(office);
        return toDto(office);
    }

    public void deleteOffice(UUID id) {
        if (!officeRepository.existsById(id)) {
            throw new EntityNotFoundException("Bureau introuvable avec l'id: " + id);
        }
        officeRepository.deleteById(id);
    }

    private OfficeDto toDto(Office office) {
        return OfficeDto.builder()
                .id(office.getId())
                .code(office.getCode())

                .communeId(office.getCommune() != null ? office.getCommune().getId() : null)
                .communeName(office.getCommune() != null ? office.getCommune().getName() : null)

                .departmentId(office.getDepartment() != null ? office.getDepartment().getId() : null)
                .departmentName(office.getDepartment() != null ? office.getDepartment().getName() : null)

                .regionId(office.getRegion() != null ? office.getRegion().getId() : null)
                .regionName(office.getRegion() != null ? office.getRegion().getName() : null)

                .nombreInscrits(office.getNombreInscrits())
                .nombreAbsences(office.getNombreAbsences())
                .nombreVotants(office.getNombreVotants())
                .bulletinsNuls(office.getBulletinsNuls())
                .build();
    }


    private void validateLogic(OfficeDto dto) {
        int total = dto.getNombreAbsences() + dto.getNombreVotants();
        if (total != dto.getNombreInscrits()) {
            throw new IllegalArgumentException(
                    "Erreur de cohérence : nombreVotants + nombreAbsences (" + total +
                            ") doit être égal à nombreInscrits (" + dto.getNombreInscrits() + ")."
            );
        }

        if (dto.getNombreVotants() < dto.getBulletinsNuls()) {
            throw new IllegalArgumentException(
                    "Erreur logique : le nombre de bulletins nuls ne peut pas dépasser le nombre de votants."
            );
        }
    }
}
