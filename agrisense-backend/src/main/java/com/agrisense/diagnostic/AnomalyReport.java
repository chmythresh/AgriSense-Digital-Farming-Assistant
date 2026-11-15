

package com.agrisense.diagnostic;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;

@Entity
@Table(name = "anomaly_reports")
public class AnomalyReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String anomalyType;

    @Column(nullable = false)
    private String sector;

    @Column(nullable = false)
    private double confidence;

    @Column(nullable = false, length = 2000)
    private String description;

    @Column(nullable = false)
    private Instant detectedAt;

    @Column(nullable = false)
    private boolean resolved;

    @Column(nullable = false, columnDefinition = "BOOLEAN NOT NULL DEFAULT FALSE")
    private boolean verified = false;

    @Column
    private String verifiedBy;

    @Column
    private Instant verifiedAt;

    protected AnomalyReport() {
    }

    public AnomalyReport(
            String anomalyType,
            String sector,
            double confidence,
            String description,
            Instant detectedAt,
            boolean resolved) {

        this.anomalyType = anomalyType;
        this.sector = sector;
        this.confidence = confidence;
        this.description = description;
        this.detectedAt = detectedAt;
        this.resolved = resolved;
        this.verified = false;
    }

    public void verify(String verifier) {
        if (verifier == null || verifier.isBlank()) {
            throw new IllegalArgumentException("Verifier identity is required");
        }

        this.verified = true;
        this.verifiedBy = verifier;
        this.verifiedAt = Instant.now();
    }

    public Long getId() {
        return id;
    }

    public String getAnomalyType() {
        return anomalyType;
    }

    public String getSector() {
        return sector;
    }

    public double getConfidence() {
        return confidence;
    }

    public String getDescription() {
        return description;
    }

    public Instant getDetectedAt() {
        return detectedAt;
    }

    public boolean isResolved() {
        return resolved;
    }

    public boolean isVerified() {
        return verified;
    }

    public String getVerifiedBy() {
        return verifiedBy;
    }

    public Instant getVerifiedAt() {
        return verifiedAt;
    }
}
