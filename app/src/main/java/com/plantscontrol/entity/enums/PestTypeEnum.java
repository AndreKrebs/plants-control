package com.plantscontrol.entity.enums;

public enum PestTypeEnum {

    INSECT("INSECT"),
    FUNGUS("FUNGUS"),
    VIRUS("VIRUS");

    private String type;

    PestTypeEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
