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

    public PlantPest() {}

    public PlantPest(Long plantId) {
        this.plantId = plantId;
    }

//    private Long pestId;

//    @NonNull
//    private Date dateRegistry;
//
//    private Boolean problemSolved;
//
//    private Date dateEndProblem;
//
//    private String solutionDescription;


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
}
