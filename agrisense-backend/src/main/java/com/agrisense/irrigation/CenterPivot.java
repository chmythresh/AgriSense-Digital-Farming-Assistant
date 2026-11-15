package com.agrisense.irrigation;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "center_pivots")
public class CenterPivot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String sector;

    @Column(nullable = false)
    private double angleDegrees;

    @Column(nullable = false)
    private double pressurePsi;

    @Column(nullable = false)
    private String nozzleStatus;

    protected CenterPivot() {
    }

    public CenterPivot(String name, String sector, double angleDegrees, double pressurePsi, String nozzleStatus) {
        this.name = name;
        this.sector = sector;
        this.angleDegrees = angleDegrees;
        this.pressurePsi = pressurePsi;
        this.nozzleStatus = nozzleStatus;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getSector() { return sector; }
    public double getAngleDegrees() { return angleDegrees; }
    public double getPressurePsi() { return pressurePsi; }
    public String getNozzleStatus() { return nozzleStatus; }
    public void setAngleDegrees(double angleDegrees) { this.angleDegrees = angleDegrees; }
}
