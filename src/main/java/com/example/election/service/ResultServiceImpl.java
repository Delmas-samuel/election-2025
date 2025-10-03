package com.example.election.service;

import com.example.election.dto.ResultDto;
import com.example.election.model.Office;
import com.example.election.model.Result;
import com.example.election.model.User;
import com.example.election.repository.OfficeRepository;
import com.example.election.repository.ResultRepository;
import com.example.election.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ResultServiceImpl implements ResultService {

    private final ResultRepository resultRepository;
    private final OfficeRepository officeRepository;
    private final UserRepository userRepository;

    @Override
    public Result submit(ResultDto dto) {
        Office office = officeRepository.findById(dto.getOfficeId())
                .orElseThrow(() -> new RuntimeException("Office not found"));
        User user = userRepository.findById(dto.getSubmittedById())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Result result = Result.builder()
                .office(office)
                .submittedBy(user)
                .votesJson(dto.getVotesJson())
                .signature(dto.getSignature())
                .status("PENDING")
                .synced(false)
                .version(1)
                .build();

        return resultRepository.save(result);
    }

    @Override
    public Result findById(UUID id) {
        return resultRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Result not found"));
    }

    @Override
    public List<Result> findByOffice(UUID officeId) {
        return resultRepository.findByOfficeId(officeId);
    }

    @Override
    public List<Result> dashboard(String region) {
        if (region == null) return resultRepository.findAll();
        return resultRepository.findByOfficeRegion(region);
    }

    @Override
    public List<Result> findAll() {
        return resultRepository.findAll();
    }
}
