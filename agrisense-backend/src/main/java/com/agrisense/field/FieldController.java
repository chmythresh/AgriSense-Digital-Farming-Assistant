package com.agrisense.field;

import java.util.List;
import java.util.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/fields")
public class FieldController {

    private final SectorParcelRepository parcels;
    private final SpectralIndexRepository indices;

    public FieldController(SectorParcelRepository parcels, SpectralIndexRepository indices) {
        this.parcels = parcels;
        this.indices = indices;
    }

    @GetMapping
    public List<SectorParcel> parcels() {
        return parcels.findAll();
    }

    @GetMapping("/{sectorCode}")
    public FieldDetails field(@PathVariable String sectorCode) {
        SectorParcel parcel = parcels.findById(sectorCode)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sector not found"));
        return new FieldDetails(parcel, indices.findById(sectorCode).orElse(null));
    }

    @GetMapping("/indices")
    public List<SpectralIndex> indices() {
        return indices.findAll();
    }

    public record FieldDetails(SectorParcel parcel, SpectralIndex spectralIndex) {
    }
}
