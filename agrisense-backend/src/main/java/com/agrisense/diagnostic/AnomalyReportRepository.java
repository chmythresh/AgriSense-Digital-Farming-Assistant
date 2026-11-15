package com.agrisense.diagnostic;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnomalyReportRepository extends JpaRepository<AnomalyReport, Long> {
    List<AnomalyReport> findByResolvedFalseOrderByDetectedAtDesc();
}
