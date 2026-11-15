package com.agrisense.irrigation;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IrrigationScheduleRepository extends JpaRepository<IrrigationSchedule, Long> {
}
