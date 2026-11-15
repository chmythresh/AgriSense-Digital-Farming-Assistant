package com.agrisense.field;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "sector_parcels")
public class SectorParcel {

    @Id
    private String sectorCode;

    @Column(nullable = false)
    private String crop;

    @Column(nullable = false)
    private double acres;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PhenologyStage phenologyStage;

    @Column(nullable = false)
    private int daysToR1;

    protected SectorParcel() {
    }

    public SectorParcel(String sectorCode, String crop, double acres, PhenologyStage phenologyStage, int daysToR1) {
        this.sectorCode = sectorCode;
        this.crop = crop;
        this.acres = acres;
        this.phenologyStage = phenologyStage;
        this.daysToR1 = daysToR1;
    }

    public String getSectorCode() { return sectorCode; }
    public String getCrop() { return crop; }
    public double getAcres() { return acres; }
    public PhenologyStage getPhenologyStage() { return phenologyStage; }
    public int getDaysToR1() { return daysToR1; }
}
