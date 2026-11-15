package com.agrisense.field;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "spectral_indices")
public class SpectralIndex {

    @Id
    private String sectorCode;

    @OneToOne
    @MapsId
    @JoinColumn(name = "sector_code")
    private SectorParcel parcel;

    @Column(nullable = false)
    private double ndvi;

    @Column(nullable = false)
    private double cwsi;

    @Column(nullable = false)
    private double leafAreaIndex;

    @Column(nullable = false)
    private double chlorophyllIndex;

    protected SpectralIndex() {
    }

    public SpectralIndex(SectorParcel parcel, double ndvi, double cwsi, double leafAreaIndex,
            double chlorophyllIndex) {
        this.parcel = parcel;
        this.ndvi = ndvi;
        this.cwsi = cwsi;
        this.leafAreaIndex = leafAreaIndex;
        this.chlorophyllIndex = chlorophyllIndex;
    }

    public String getSectorCode() { return sectorCode; }
    public SectorParcel getParcel() { return parcel; }
    public double getNdvi() { return ndvi; }
    public double getCwsi() { return cwsi; }
    public double getLeafAreaIndex() { return leafAreaIndex; }
    public double getChlorophyllIndex() { return chlorophyllIndex; }
}
