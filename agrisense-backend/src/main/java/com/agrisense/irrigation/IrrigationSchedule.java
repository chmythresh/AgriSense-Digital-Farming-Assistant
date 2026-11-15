package com.agrisense.irrigation;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "irrigation_schedules")
public class IrrigationSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "pivot_id", nullable = false)
    private CenterPivot pivot;

    @Column(nullable = false)
    private LocalDateTime startAt;

    @Column(nullable = false)
    private int durationMinutes;

    @Column(nullable = false)
    private int nightCycleAdjustmentMinutes;

    @Column(nullable = false)
    private double waterGallons;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ScheduleStatus status;

    protected IrrigationSchedule() {
    }

    public IrrigationSchedule(CenterPivot pivot, LocalDateTime startAt, int durationMinutes,
            int nightCycleAdjustmentMinutes, double waterGallons, ScheduleStatus status) {
        this.pivot = pivot;
        this.startAt = startAt;
        this.durationMinutes = durationMinutes;
        this.nightCycleAdjustmentMinutes = nightCycleAdjustmentMinutes;
        this.waterGallons = waterGallons;
        this.status = status;
    }

    public Long getId() { return id; }
    public CenterPivot getPivot() { return pivot; }
    public LocalDateTime getStartAt() { return startAt; }
    public int getDurationMinutes() { return durationMinutes; }
    public int getNightCycleAdjustmentMinutes() { return nightCycleAdjustmentMinutes; }
    public double getWaterGallons() { return waterGallons; }
    public ScheduleStatus getStatus() { return status; }
}
