package com.example.election.service;

import com.example.election.model.Office;
import com.example.election.model.PvFile;
import com.example.election.model.Result;
import com.example.election.model.User;
import com.example.election.repository.OfficeRepository;
import com.example.election.repository.PvFileRepository;
import com.example.election.repository.ResultRepository;
import com.example.election.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PvFileService {

    private final PvFileRepository pvFileRepository;
    private final ResultRepository resultRepository;
    private final UserRepository userRepository;
    private final OfficeRepository officeRepository;

    public PvFile upload(MultipartFile file, UUID resultId, UUID userId, UUID officeId) {
        // Taille max : 5 Mo
        if (file.getSize() > 5 * 1024 * 1024) {
            throw new IllegalArgumentException("La taille du fichier dépasse 5 Mo !");
        }

        // Format autorisé
        String contentType = file.getContentType();
        if (contentType == null ||
                !(contentType.equals("image/jpeg") ||
                        contentType.equals("image/png") ||
                        contentType.equals("application/pdf"))) {
            throw new IllegalArgumentException("Format non autorisé (JPEG, PNG, PDF uniquement).");
        }

        // Entités liées
        Result result = resultRepository.findById(resultId)
                .orElseThrow(() -> new IllegalArgumentException("Résultat introuvable"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur introuvable"));

        Office office = officeRepository.findById(officeId)
                .orElseThrow(() -> new IllegalArgumentException("Bureau introuvable"));

        // Construction de l'entité PvFile
        PvFile pvFile = PvFile.builder()
                .filename(file.getOriginalFilename())
                .contentType(contentType)
                .sizeBytes(file.getSize())
                .result(result)
                .user(user)
                .office(office)
                .build();

        return pvFileRepository.save(pvFile);
    }

    public PvFile findById(UUID id) {
        return pvFileRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Fichier introuvable"));
    }
}
