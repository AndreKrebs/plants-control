package com.plantscontrol.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;

import static androidx.room.ForeignKey.CASCADE;
import static androidx.room.ForeignKey.RESTRICT;

@Entity
public class PlantPest {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    @ForeignKey
            (entity = Plant.class,
                    parentColumns = "id",
                    childColumns = "plantId",
                    onDelete = RESTRICT)
    private Long plantId;

    @ForeignKey
            (entity = Pest.class,
                    parentColumns = "id",
                    childColumns = "pesttId",
                    onDelete = RESTRICT)
    private Long pestId;

    private Date dateDetectedPest;

    private Boolean problemResolved;

    private Date dateProblemResolved;

    private String descriptionResolved;

    public PlantPest() {}

    public PlantPest(Long plantId) {
        this.plantId = plantId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPlantId() {
        return plantId;
    }

    public void setPlantId(Long plantId) {
        this.plantId = plantId;
    }

    public Long getPestId() {
        return pestId;
    }

    public void setPestId(Long pestId) {
        this.pestId = pestId;
    }

    public Date getDateDetectedPest() {
        return dateDetectedPest;
    }

    public void setDateDetectedPest(Date dateDetectedPest) {
        this.dateDetectedPest = dateDetectedPest;
    }

    public Boolean isProblemResolved() {
        return problemResolved;
    }

    public void setProblemResolved(Boolean problemResolved) {
        this.problemResolved = problemResolved;
    }

    public Date getDateProblemResolved() {
        return dateProblemResolved;
    }

    public void setDateProblemResolved(Date dateProblemResolved) {
        this.dateProblemResolved = dateProblemResolved;
    }

    public String getDescriptionResolved() {
        return descriptionResolved;
    }

    public void setDescriptionResolved(String descriptionResolved) {
        this.descriptionResolved = descriptionResolved;
    }
}
