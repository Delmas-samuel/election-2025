package com.example.election.controller;

import com.example.election.dto.VoteDto;
import com.example.election.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/votes")
@RequiredArgsConstructor
public class VoteController {

    private final VoteService voteService;

    @GetMapping
    public ResponseEntity<List<VoteDto>> getAll() {
        return ResponseEntity.ok(voteService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VoteDto> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(voteService.getById(id));
    }

    @PostMapping
    public ResponseEntity<VoteDto> create(@RequestBody VoteDto dto) {
        return ResponseEntity.ok(voteService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VoteDto> update(@PathVariable UUID id, @RequestBody VoteDto dto) {
        return ResponseEntity.ok(voteService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        voteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
