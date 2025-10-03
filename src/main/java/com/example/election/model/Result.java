package com.example.election.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;


import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "results")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Result {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bureau_id")
    private Office office;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "submitted_by")
    private User submittedBy;

    @CreationTimestamp
    private Instant submittedAt;

    @Column(columnDefinition = "jsonb", nullable = false)
    private String votesJson;

    @Lob
    private byte[] signature;

    @Column(length = 20)
    private String status; // PENDING, VALIDATED, REJECTED

    private boolean synced;

    private int version;

    @OneToMany(mappedBy = "result")
    @JsonIgnore
    private Set<PvFile> pvFiles;
}
