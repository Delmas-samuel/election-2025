package com.example.election.service;


import com.example.election.model.Region;
import com.example.election.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RegionService {

    private final RegionRepository regionRepository;

    /**
     * Créer une région
     */
    public Region createRegion(String name) {
        Region region = Region.builder()
                .name(name)
                .build();

        return regionRepository.save(region);
    }

    /**
     * Récupérer toutes les régions
     */
    public List<Region> getAllRegions() {
        return regionRepository.findAll();
    }

    /**
     * Récupérer une région par son ID
     */
    public Region getRegionById(Long id) {
        return regionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Région introuvable"));
    }
}
