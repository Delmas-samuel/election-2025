package com.example.election.service;

import com.example.election.dto.LoginRequest;
import com.example.election.dto.OtpVerifyRequest;
import com.example.election.dto.UserDto;
import com.example.election.model.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    User register(UserDto dto);
    String login(LoginRequest request);
    boolean verifyOtp(OtpVerifyRequest request);
    User findById(UUID id);
    void delete(UUID id);

    List<User> findAll();
}
