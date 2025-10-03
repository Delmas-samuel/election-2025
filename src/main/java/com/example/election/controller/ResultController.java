package com.example.election.controller;

import com.example.election.dto.ResultDto;
import com.example.election.model.Result;
import com.example.election.service.ResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/results")
@RequiredArgsConstructor
public class ResultController {
    private final ResultService resultService;

    @PostMapping
    public ResponseEntity<Result> submitResult(@RequestBody ResultDto dto) {
        return ResponseEntity.ok(resultService.submit(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Result> getResult(@PathVariable UUID id) {
        return ResponseEntity.ok(resultService.findById(id));
    }

    @GetMapping("/office/{officeId}")
    public ResponseEntity<List<Result>> getByOffice(@PathVariable UUID officeId) {
        return ResponseEntity.ok(resultService.findByOffice(officeId));
    }

    @GetMapping("/dashboard")
    public ResponseEntity<List<Result>> dashboard(@RequestParam(required = false) String region) {
        return ResponseEntity.ok(resultService.dashboard(region));
    }

    @GetMapping
    public ResponseEntity<List<Result>> getAllResults() {
        return ResponseEntity.ok(resultService.findAll());
    }
}
