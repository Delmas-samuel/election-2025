package com.example.election.service;

import com.example.election.dto.VoteDto;
import com.example.election.model.Candidate;
import com.example.election.model.Office;
import com.example.election.model.Vote;
import com.example.election.repository.CandidateRepository;
import com.example.election.repository.OfficeRepository;
import com.example.election.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;
    private final CandidateRepository candidateRepository;
    private final OfficeRepository officeRepository;

    public List<VoteDto> getAll() {
        return voteRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public VoteDto getById(UUID id) {
        Vote vote = voteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vote introuvable"));
        return toDto(vote);
    }

    public VoteDto create(VoteDto dto) {
        Candidate candidate = candidateRepository.findById(dto.getCandidateId())
                .orElseThrow(() -> new IllegalArgumentException("Candidat introuvable"));
        Office office = officeRepository.findById(dto.getOfficeId())
                .orElseThrow(() -> new IllegalArgumentException("Bureau introuvable"));

        Vote vote = Vote.builder()
                .candidate(candidate)
                .office(office)
                .maleVoters(dto.getMaleVoters())
                .femaleVoters(dto.getFemaleVoters())
                .build();

        return toDto(voteRepository.save(vote));
    }

    public VoteDto update(UUID id, VoteDto dto) {
        Vote vote = voteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vote introuvable"));

        Candidate candidate = candidateRepository.findById(dto.getCandidateId())
                .orElseThrow(() -> new IllegalArgumentException("Candidat introuvable"));
        Office office = officeRepository.findById(dto.getOfficeId())
                .orElseThrow(() -> new IllegalArgumentException("Bureau introuvable"));

        vote.setCandidate(candidate);
        vote.setOffice(office);
        vote.setMaleVoters(dto.getMaleVoters());
        vote.setFemaleVoters(dto.getFemaleVoters());

        return toDto(voteRepository.save(vote));
    }

    public void delete(UUID id) {
        if (!voteRepository.existsById(id)) {
            throw new IllegalArgumentException("Vote introuvable");
        }
        voteRepository.deleteById(id);
    }

    private VoteDto toDto(Vote vote) {
        VoteDto dto = new VoteDto();
        dto.setId(vote.getId());
        dto.setCandidateId(vote.getCandidate().getId());
        dto.setOfficeId(vote.getOffice().getId());
        dto.setMaleVoters(vote.getMaleVoters());
        dto.setFemaleVoters(vote.getFemaleVoters());
        return dto;
    }
}
