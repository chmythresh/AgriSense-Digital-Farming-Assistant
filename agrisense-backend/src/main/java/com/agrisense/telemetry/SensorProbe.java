package com.agrisense.telemetry;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "sensor_probes")
public class SensorProbe {

    @Id
    private String probeId;

    @Column(nullable = false)
    private double soilMoisturePercent;

    @Column(nullable = false)
    private double soilTemperatureCelsius;

    @Column(nullable = false)
    private boolean online;

    @Column(nullable = false)
    private Instant lastUpdated;

    protected SensorProbe() {
    }

    public SensorProbe(String probeId, double soilMoisturePercent, double soilTemperatureCelsius,
            boolean online, Instant lastUpdated) {
        this.probeId = probeId;
        this.soilMoisturePercent = soilMoisturePercent;
        this.soilTemperatureCelsius = soilTemperatureCelsius;
        this.online = online;
        this.lastUpdated = lastUpdated;
    }

    public String getProbeId() { return probeId; }
    public double getSoilMoisturePercent() { return soilMoisturePercent; }
    public double getSoilTemperatureCelsius() { return soilTemperatureCelsius; }
    public boolean isOnline() { return online; }
    public Instant getLastUpdated() { return lastUpdated; }
}
