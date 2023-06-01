package com.projects.ski_resort_project.enums;

public enum Attributes {
    RESORTS("resorts"),
    NEWS("news"),
    EVENTS("events"),
    USER("user"),
    ERRORS("errors");

    private final String value;

    Attributes(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
