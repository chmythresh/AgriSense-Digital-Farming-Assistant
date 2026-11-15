package com.agrisense.telemetry;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/telemetry")
public class TelemetryController {

    private final SensorProbeRepository probes;
    private final GatewayStatusRepository gateways;

    public TelemetryController(SensorProbeRepository probes, GatewayStatusRepository gateways) {
        this.probes = probes;
        this.gateways = gateways;
    }

    @GetMapping
    public TelemetrySnapshot snapshot() {
        return new TelemetrySnapshot(
                gateways.findAll().stream().findFirst()
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Gateway not configured")),
                probes.findAll());
    }

    @GetMapping("/probes")
    public List<SensorProbe> probes() {
        return probes.findAll();
    }

    @GetMapping("/gateway")
    public GatewayStatus gateway() {
        return gateways.findAll().stream().findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Gateway not configured"));
    }

    public record TelemetrySnapshot(GatewayStatus gateway, List<SensorProbe> probes) {
    }
}
