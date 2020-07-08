package com.plantscontrol.entity;

public class Pest {
    private String popularName;
    private String scientificName;
    private String type;
    private String weather;

    public Pest(String popularName, String scientificName, String type, String weather) {
        setPopularName(popularName);
        setScientificName(scientificName);
        setType(type);
        setWeather(weather);
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

    @Override
    public String toString() {
        return "Nome: " + getPopularName() + " - " + getScientificName() + "\nTipo:" + getType() + " / Clima:" + getWeather();
    }
}
