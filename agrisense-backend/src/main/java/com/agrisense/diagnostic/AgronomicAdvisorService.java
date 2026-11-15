package com.agrisense.diagnostic;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AgronomicAdvisorService {

    private final AnomalyReportRepository reports;

    public AgronomicAdvisorService(AnomalyReportRepository reports) {
        this.reports = reports;
    }

    public List<Recommendation> recommendations() {
        List<Recommendation> recommendations = new ArrayList<>();
        for (AnomalyReport report : reports.findByResolvedFalseOrderByDetectedAtDesc()) {
            if (report.getAnomalyType().equalsIgnoreCase("APHID_CLUSTER") && report.getConfidence() >= 0.7) {
                recommendations.add(new Recommendation(report.getSector(), "Inspect leaf undersides and scout the affected perimeter within 24 hours.",
                        "HIGH", report.getId()));
            } else if (report.getAnomalyType().equalsIgnoreCase("FUNGAL_RISK") && report.getConfidence() >= 0.6) {
                recommendations.add(new Recommendation(report.getSector(), "Check canopy wetness and improve airflow; reassess before applying treatment.",
                        "MEDIUM", report.getId()));
            } else if (report.getAnomalyType().equalsIgnoreCase("WATER_STRESS") && report.getConfidence() >= 0.6) {
                recommendations.add(new Recommendation(report.getSector(), "Review probe moisture readings and consider a targeted irrigation cycle.",
                        "MEDIUM", report.getId()));
            }
        }
        return List.copyOf(recommendations);
    }

    public record Recommendation(String sector, String recommendation, String priority, Long anomalyId) {
    }
}
