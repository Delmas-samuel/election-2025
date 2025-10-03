package com.example.election.controller;

import com.example.election.dto.CommuneDTO;
import com.example.election.service.CommuneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/communes")
@RequiredArgsConstructor
public class CommuneController {

    private final CommuneService communeService;

    @GetMapping
    public ResponseEntity<List<CommuneDTO>> getAll() {
        return ResponseEntity.ok(communeService.getAllCommunes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommuneDTO> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(communeService.getById(id));
    }

    @PostMapping
    public ResponseEntity<CommuneDTO> create(@RequestBody CommuneDTO dto) {
        return ResponseEntity.ok(communeService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommuneDTO> update(@PathVariable UUID id, @RequestBody CommuneDTO dto) {
        return ResponseEntity.ok(communeService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        communeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
