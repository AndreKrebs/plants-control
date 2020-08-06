package com.plantscontrol.entity.enums;

public enum PestPropagationSpeedEnum {

    SLOW("SLOW"),
    MODERATE("MODERATE"),
    FAST("FAST");

    private String speedPropagation;

    PestPropagationSpeedEnum(String speedPropagation) {
        this.speedPropagation = speedPropagation;
    }

    public String getPropagationSpeed() {
        return speedPropagation;
    }

}
