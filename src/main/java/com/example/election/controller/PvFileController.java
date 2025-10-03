package com.example.election.controller;

import com.example.election.model.PvFile;
import com.example.election.service.PvFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/api/pv-files")
@RequiredArgsConstructor
public class PvFileController {

    private final PvFileService pvFileService;

    @PostMapping("/upload")
    public ResponseEntity<PvFile> upload(
            @RequestParam("file") MultipartFile file,
            @RequestParam UUID resultId,
            @RequestParam UUID userId,
            @RequestParam UUID officeId
    ) {
        return ResponseEntity.ok(pvFileService.upload(file, resultId, userId, officeId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PvFile> getFile(@PathVariable UUID id) {
        return ResponseEntity.ok(pvFileService.findById(id));
    }
}
