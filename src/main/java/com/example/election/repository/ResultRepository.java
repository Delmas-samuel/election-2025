package com.example.election.repository;

import com.example.election.model.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ResultRepository extends JpaRepository<Result, UUID> {
    List<Result> findByOfficeId(UUID officeId);

    @Query("SELECT r FROM Result r WHERE r.office.commune.department.region.name = :region")
    List<Result> findByOfficeRegion(@Param("region") String region);
}
