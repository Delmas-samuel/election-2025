package com.example.election.repository;


import com.example.election.model.PvFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PvFileRepository extends JpaRepository<PvFile, UUID> {
}
