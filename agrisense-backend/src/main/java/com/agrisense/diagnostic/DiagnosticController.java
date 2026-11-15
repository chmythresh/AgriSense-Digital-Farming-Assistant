
package com.agrisense.diagnostic;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/diagnostics")
public class DiagnosticController {

    private final AnomalyReportRepository reports;
    private final AgronomicAdvisorService advisor;

    public DiagnosticController(
            AnomalyReportRepository reports,
            AgronomicAdvisorService advisor) {
        this.reports = reports;
        this.advisor = advisor;
    }

    @GetMapping("/anomalies")
    public List<AnomalyReport> anomalies() {
        return reports.findAll();
    }

    @GetMapping("/recommendations")
    public List<AgronomicAdvisorService.Recommendation> recommendations() {
        return advisor.recommendations();
    }

    @PutMapping("/anomalies/{id}/verify")
    @PreAuthorize("hasAnyRole('AGRONOMIST', 'FARM_OWNER')")
    public AnomalyReport verifyAnomaly(
            @PathVariable long id,
            Authentication authentication) {

        AnomalyReport report = reports.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Anomaly not found"));

        report.verify(authentication.getName());

        return reports.save(report);
    }
}
