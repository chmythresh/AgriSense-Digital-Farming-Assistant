package com.agrisense.telemetry;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GatewayStatusRepository extends JpaRepository<GatewayStatus, Long> {
}
