package com.example.election.service;

import com.example.election.dto.LoginRequest;
import com.example.election.dto.OtpVerifyRequest;
import com.example.election.dto.UserDto;
import com.example.election.model.Office;
import com.example.election.model.Role;
import com.example.election.model.User;
import com.example.election.repository.OfficeRepository;
import com.example.election.repository.RoleRepository;
import com.example.election.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final OfficeRepository officeRepository;
    private final PasswordEncoder passwordEncoder;
    private final OtpService otpService;

    @Override
    public User register(UserDto dto) {
        Role role = roleRepository.findById(dto.getRoleId())
                .orElseThrow(() -> new RuntimeException("Role not found"));
        Office office = officeRepository.findById(dto.getOfficeId())
                .orElseThrow(() -> new RuntimeException("Office not found"));

        // Génération d’un mot de passe aléatoire
        String generatedPassword = generateRandomPassword();

        User user = User.builder()
                .numero(dto.getNumero())
                .email(dto.getEmail())
                .passwordHash(passwordEncoder.encode(generatedPassword))
                .role(role)
                .office(office)
                .build();

        User savedUser = userRepository.save(user);

        otpService.generateOtp(savedUser.getId());

        return savedUser;
    }

    @Override
    public String login(LoginRequest request) {
        User user = userRepository.findByNumero(request.getNumero())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new RuntimeException("Invalid password");
        }

        return otpService.generateOtp(user.getId());
    }

    @Override
    public boolean verifyOtp(OtpVerifyRequest request) {
        return otpService.validateOtp(request);
    }

    @Override
    public User findById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public void delete(UUID id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    // Méthode utilitaire pour générer un mot de passe aléatoire
    private String generateRandomPassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(8);
        for (int i = 0; i < 8; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }
}
