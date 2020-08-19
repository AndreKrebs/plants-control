package com.plantscontrol.entity.dto;

public class PlantPestDTO {

    private Long id;
    private String plantPopularName;
    private String pestPopularName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlantPopularName() {
        return plantPopularName;
    }

    public void setPlantPopularName(String plantPopularName) {
        this.plantPopularName = plantPopularName;
    }

    public String getPestPopularName() {
        return pestPopularName;
    }

    public void setPestPopularName(String pestPopularName) {
        this.pestPopularName = pestPopularName;
    }
}
