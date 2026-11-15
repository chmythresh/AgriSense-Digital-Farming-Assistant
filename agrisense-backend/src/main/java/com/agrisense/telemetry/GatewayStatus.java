package com.agrisense.telemetry;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "gateway_status")
public class GatewayStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String gatewayName;

    @Column(nullable = false)
    private int pingMillis;

    @Column(nullable = false)
    private double streamUptimePercent;

    @Column(nullable = false)
    private String firmwareVersion;

    protected GatewayStatus() {
    }

    public GatewayStatus(String gatewayName, int pingMillis, double streamUptimePercent, String firmwareVersion) {
        this.gatewayName = gatewayName;
        this.pingMillis = pingMillis;
        this.streamUptimePercent = streamUptimePercent;
        this.firmwareVersion = firmwareVersion;
    }

    public Long getId() { return id; }
    public String getGatewayName() { return gatewayName; }
    public int getPingMillis() { return pingMillis; }
    public double getStreamUptimePercent() { return streamUptimePercent; }
    public String getFirmwareVersion() { return firmwareVersion; }
}
