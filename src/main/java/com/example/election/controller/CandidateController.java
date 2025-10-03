package com.example.election.controller;

import com.example.election.dto.CandidateDto;
import com.example.election.service.CandidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/candidates")
@RequiredArgsConstructor
public class CandidateController {

    private final CandidateService candidateService;

    @GetMapping
    public ResponseEntity<List<CandidateDto>> getAll() {
        return ResponseEntity.ok(candidateService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CandidateDto> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(candidateService.getById(id));
    }

    @PostMapping
    public ResponseEntity<CandidateDto> create(@RequestBody CandidateDto dto) {
        return ResponseEntity.ok(candidateService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CandidateDto> update(@PathVariable UUID id, @RequestBody CandidateDto dto) {
        return ResponseEntity.ok(candidateService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        candidateService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
