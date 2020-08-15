package com.plantscontrol.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity
public class Plant {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    @NonNull
    private String identificationPlantGarden;

    @NonNull
    private String popularName;

    @NonNull
    private String scientificName;

    public Plant(){}

    public Plant(Long id, @NonNull String identificationPlantGarden, @NonNull String popularName, @NonNull String scientificName) {
        this.id = id;
        this.identificationPlantGarden = identificationPlantGarden;
        this.popularName = popularName;
        this.scientificName = scientificName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NonNull
    public String getIdentificationPlantGarden() {
        return identificationPlantGarden;
    }

    public void setIdentificationPlantGarden(@NonNull String identificationPlantGarden) {
        this.identificationPlantGarden = identificationPlantGarden;
    }

    @NonNull
    public String getPopularName() {
        return popularName;
    }

    public void setPopularName(@NonNull String popularName) {
        this.popularName = popularName;
    }

    @NonNull
    public String getScientificName() {
        return scientificName;
    }

    public void setScientificName(@NonNull String scientificName) {
        this.scientificName = scientificName;
    }
}
