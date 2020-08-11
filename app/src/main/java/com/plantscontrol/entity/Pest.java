package com.plantscontrol.entity;

import com.plantscontrol.R;
import java.io.Serializable;
import android.content.res.Resources;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Pest implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    @NonNull
    private String popularName;

    @NonNull
    private String scientificName;

    @NonNull
    private String type;

    private String weather;

    @NonNull
    private String description;

    private String velocity;

    private String methodsDescription;

    public Pest() {
    }

    public Pest(String popularName, String scientificName, String type) {
        this.popularName = popularName;
        this.scientificName = scientificName;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPopularName() {
        return popularName;
    }

    public void setPopularName(String popularName) {
        this.popularName = popularName;
    }

    public String getScientificName() {
        return scientificName;
    }

    public void setScientificName(String scientificName) {
        this.scientificName = scientificName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVelocity() {
        return velocity;
    }

    public void setVelocity(String velocity) {
        this.velocity = velocity;
    }

    public String getMethodsDescription() {
        return methodsDescription;
    }

    public void setMethodsDescription(String methodsDescription) {
        this.methodsDescription = methodsDescription;
    }

    @Override
    public String toString() {

        return getPopularName() + " - " + getScientificName() + "\n"
                + getType() + " / " + getWeather();
    }

}