package com.agrisense.telemetry;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorProbeRepository extends JpaRepository<SensorProbe, String> {
}
