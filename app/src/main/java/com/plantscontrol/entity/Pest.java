package com.plantscontrol.entity;

import java.io.Serializable;
import java.util.Comparator;

public class Pest implements Serializable {
    private Long id;
    private String popularName;
    private String scientificName;
    private String type;
    private String weather;
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
        return "Nome: " + getPopularName() + " - " + getScientificName() + "\nTipo:" + getType() + " / Clima:" + getWeather();
    }

}