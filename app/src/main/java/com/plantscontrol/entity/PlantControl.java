package com.plantscontrol.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class PlantControl {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    private Plant plant;

    private Pest pest;

    @NonNull
    private Date dateRegistry;

    private Boolean problemSolved;

    private Date dateEndProblem;

    private String solutionDescription;

}
