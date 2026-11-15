package com.agrisense;

import com.agrisense.auth.AppUser;
import com.agrisense.auth.AppUserRepository;
import com.agrisense.auth.UserRole;
import com.agrisense.diagnostic.AnomalyReport;
import com.agrisense.diagnostic.AnomalyReportRepository;
import com.agrisense.field.PhenologyStage;
import com.agrisense.field.SectorParcel;
import com.agrisense.field.SectorParcelRepository;
import com.agrisense.field.SpectralIndex;
import com.agrisense.field.SpectralIndexRepository;
import com.agrisense.irrigation.CenterPivot;
import com.agrisense.irrigation.CenterPivotRepository;
import com.agrisense.irrigation.IrrigationSchedule;
import com.agrisense.irrigation.IrrigationScheduleRepository;
import com.agrisense.irrigation.ScheduleStatus;
import com.agrisense.telemetry.GatewayStatus;
import com.agrisense.telemetry.GatewayStatusRepository;
import com.agrisense.telemetry.SensorProbe;
import com.agrisense.telemetry.SensorProbeRepository;
import java.time.Instant;
import java.time.LocalDateTime;
import org.springframework.lang.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

@Configuration
public class AgriSenseDataInitializer {

    @Bean
    ApplicationRunner seedDemoData(
            AppUserRepository users,
            PasswordEncoder passwordEncoder,
            @Value("${agrisense.demo.password}") String demoPassword,
            SensorProbeRepository probes,
            GatewayStatusRepository gateways,
            SectorParcelRepository parcels,
            SpectralIndexRepository indices,
            CenterPivotRepository pivots,
            IrrigationScheduleRepository schedules,
            AnomalyReportRepository reports,
            @NonNull PlatformTransactionManager transactionManager) {
        return args -> {
            new TransactionTemplate(transactionManager).executeWithoutResult(status -> {
                if (users.count() == 0) {
                    String passwordHash = passwordEncoder.encode(demoPassword);
                    users.save(new AppUser("marcus.vance@highlandridge.ag", passwordHash, "Marcus Vance",
                            UserRole.AGRONOMIST));
                    users.save(new AppUser("elena.reed@highlandridge.ag", passwordHash, "Elena Reed",
                            UserRole.FARM_OWNER));
                    users.save(new AppUser("tech@highlandridge.ag", passwordHash, "Hardware Technician",
                            UserRole.HARDWARE_TECH));
                }
                if (probes.count() == 0) {
                    Instant now = Instant.now();
                    probes.save(new SensorProbe("S-12", 28.4, 23.4, true, now));
                    probes.save(new SensorProbe("S-14", 31.2, 22.8, true, now));
                    probes.save(new SensorProbe("S-15", 24.9, 24.1, true, now));
                }
                if (gateways.count() == 0) {
                    gateways.save(new GatewayStatus("04-Nordic Gateway", 14, 99.98, "v3.8.4-ag"));
                }
                if (parcels.count() == 0) {
                    seedParcel(parcels, indices, "A", "Winter Wheat", 380, PhenologyStage.V8, 24, .81, .21, 4.2,
                            46.0);
                    seedParcel(parcels, indices, "B-1", "Sweet Corn", 85.2, PhenologyStage.V8, 8, .78, .24, 4.5,
                            48.0);
                    seedParcel(parcels, indices, "B-2", "Sweet Corn", 92.4, PhenologyStage.V8, 7, .74, .31, 4.0,
                            44.0);
                    seedParcel(parcels, indices, "B-3", "Sweet Corn", 42.5, PhenologyStage.V8, 6, .69, .38, 3.8,
                            41.0);
                    seedParcel(parcels, indices, "B-4", "Sweet Corn", 99.9, PhenologyStage.TASSELING, 3, .83, .19,
                            4.8, 52.0);
                    seedParcel(parcels, indices, "C", "Organic Soybeans", 410, PhenologyStage.EMERGENCE, 32, .86, .16,
                            5.1, 55.0);
                    seedParcel(parcels, indices, "D", "Apple Orchards", 310, PhenologyStage.R1, 0, .79, .23, 3.9,
                            43.0);
                }
                if (pivots.count() == 0) {
                    CenterPivot alpha = pivots.save(new CenterPivot("Alpha-1", "A", 180, 42, "OPERATIONAL"));
                    CenterPivot beta = pivots.save(new CenterPivot("Beta-2", "B", 135, 39, "OPERATIONAL"));
                    schedules.save(new IrrigationSchedule(alpha, LocalDateTime.now().plusDays(1).withHour(2)
                            .withMinute(25).withSecond(0).withNano(0), 55, 25, 184500, ScheduleStatus.SCHEDULED));
                    schedules.save(new IrrigationSchedule(beta, LocalDateTime.now().plusDays(1).withHour(3)
                            .withMinute(0).withSecond(0).withNano(0), 40, 25, 92000, ScheduleStatus.SCHEDULED));
                }
                if (reports.count() == 0) {
                    Instant now = Instant.now();
                    reports.save(new AnomalyReport("APHID_CLUSTER", "B-3", 0.78,
                            "Aphid cluster detected in the canopy; scout the affected plants.", now, false));
                    reports.save(new AnomalyReport("FUNGAL_RISK", "B-2", 0.67,
                            "Fungal pressure elevated after overnight humidity.", now.minusSeconds(1800), false));
                    reports.save(new AnomalyReport("WATER_STRESS", "B-3", 0.72,
                            "Moisture readings are below the target range.", now.minusSeconds(3600), false));
                }
            });
        };
    }

    private static void seedParcel(SectorParcelRepository parcels, SpectralIndexRepository indices,
            String code, String crop, double acres, PhenologyStage stage, int daysToR1,
            double ndvi, double cwsi, double lai, double chlorophyll) {
        SectorParcel parcel = parcels.save(new SectorParcel(code, crop, acres, stage, daysToR1));
        indices.save(new SpectralIndex(parcel, ndvi, cwsi, lai, chlorophyll));
    }
}
