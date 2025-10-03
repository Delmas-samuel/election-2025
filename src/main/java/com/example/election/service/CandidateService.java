package com.example.election.service;

import com.example.election.dto.CandidateDto;
import com.example.election.model.Candidate;
import com.example.election.repository.CandidateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CandidateService {

    private final CandidateRepository candidateRepository;

    public List<CandidateDto> getAll() {
        return candidateRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public CandidateDto getById(UUID id) {
        Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Candidat introuvable"));
        return toDto(candidate);
    }

    public CandidateDto create(CandidateDto dto) {
        Candidate candidate = Candidate.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .partyName(dto.getPartyName())
                .build();
        return toDto(candidateRepository.save(candidate));
    }

    public CandidateDto update(UUID id, CandidateDto dto) {
        Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Candidat introuvable"));

        candidate.setFirstName(dto.getFirstName());
        candidate.setLastName(dto.getLastName());
        candidate.setPartyName(dto.getPartyName());

        return toDto(candidateRepository.save(candidate));
    }

    public void delete(UUID id) {
        if (!candidateRepository.existsById(id)) {
            throw new IllegalArgumentException("Candidat introuvable");
        }
        candidateRepository.deleteById(id);
    }

    private CandidateDto toDto(Candidate candidate) {
        CandidateDto dto = new CandidateDto();
        dto.setId(candidate.getId());
        dto.setFirstName(candidate.getFirstName());
        dto.setLastName(candidate.getLastName());
        dto.setPartyName(candidate.getPartyName());
        return dto;
    }
}
