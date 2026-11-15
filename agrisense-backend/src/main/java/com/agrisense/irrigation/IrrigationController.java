package com.agrisense.irrigation;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/irrigation")
public class IrrigationController {

    private final CenterPivotRepository pivots;
    private final IrrigationScheduleRepository schedules;

    public IrrigationController(CenterPivotRepository pivots, IrrigationScheduleRepository schedules) {
        this.pivots = pivots;
        this.schedules = schedules;
    }

    @GetMapping("/pivots")
    public List<CenterPivot> pivots() {
        return pivots.findAll();
    }

    @GetMapping("/schedules")
    public List<IrrigationSchedule> schedules() {
        return schedules.findAll();
    }

    @PostMapping("/schedules")
    @PreAuthorize("hasAnyRole('AGRONOMIST', 'FARM_OWNER')")
    public IrrigationSchedule createSchedule(@Valid @RequestBody ScheduleRequest request) {
        CenterPivot pivot = pivots.findById(request.pivotId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pivot not found"));
        return schedules.save(new IrrigationSchedule(pivot, request.startAt(), request.durationMinutes(),
                request.nightCycleAdjustmentMinutes(), request.waterGallons(), ScheduleStatus.SCHEDULED));
    }

    @PutMapping("/pivots/{id}/angle")
    @PreAuthorize("hasAnyRole('AGRONOMIST', 'FARM_OWNER')")
    public CenterPivot updatePivotAngle(@PathVariable Long id, @Valid @RequestBody AngleRequest request) {
        CenterPivot pivot = pivots.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pivot not found"));
        pivot.setAngleDegrees(request.angleDegrees());
        return pivots.save(pivot);
    }

    public record ScheduleRequest(
            @NotNull Long pivotId,
            @NotNull @FutureOrPresent LocalDateTime startAt,
            @Min(1) int durationMinutes,
            @Min(0) int nightCycleAdjustmentMinutes,
            @DecimalMin("0.1") double waterGallons) {
    }

    public record AngleRequest(@NotNull @DecimalMin("0.0") @DecimalMax("360.0") Double angleDegrees) {
    }
}
