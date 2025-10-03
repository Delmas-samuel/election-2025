package com.example.election.service;

import com.example.election.dto.ResultDto;
import com.example.election.model.Result;

import java.util.List;
import java.util.UUID;

public interface ResultService {
    Result submit(ResultDto dto);
    Result findById(UUID id);
    List<Result> findByOffice(UUID officeId);
    List<Result> dashboard(String region);

    List<Result> findAll();
}
