package com.plantscontrol.entity.enums;

public enum PestWeatherEnum {

    EQUATORIAL("EQUATORIAL"),
    TROPICAL("TROPICAL"),
    SUBTROPICAL("SUBTROPICAL"),
    DESERT("DESERT"),
    TEMPERATE("TEMPERATE"),
    MEDITERRANEAN("MEDITERRANEAN"),
    SEMI_ARID("SEMI_ARID"),
    CONTINENTAL_ARID("CONTINENTAL_ARID"),
    MOUNTAIN_COLD("MOUNTAIN_COLD"),
    POLAR("POLAR");


    private String weather;

    PestWeatherEnum(String weather) {
        this.weather = weather;
    }

    public String getWeather() {
        return weather;
    }

}
