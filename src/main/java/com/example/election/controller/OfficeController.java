package com.example.election.controller;


import com.example.election.dto.OfficeDto;
import com.example.election.service.OfficeService;
import lombok.RequiredArgsConstructor;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/offices")
@RequiredArgsConstructor
public class OfficeController {

    private final OfficeService officeService;

    @GetMapping
    public ResponseEntity<List<OfficeDto>> getAll() {
        return ResponseEntity.ok(officeService.getAllOffices());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OfficeDto> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(officeService.getOfficeById(id));
    }

    @PostMapping
    public ResponseEntity<OfficeDto> create(@RequestBody OfficeDto dto) {
        return ResponseEntity.ok(officeService.createOffice(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OfficeDto> update(@PathVariable UUID id, @RequestBody OfficeDto dto) {
        return ResponseEntity.ok(officeService.updateOffice(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        officeService.deleteOffice(id);
        return ResponseEntity.noContent().build();
    }
}
