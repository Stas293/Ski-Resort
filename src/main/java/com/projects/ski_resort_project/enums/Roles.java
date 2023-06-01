package com.projects.ski_resort_project.enums;

public enum Roles {
    ROLE_USER,
    ROLE_ADMIN;


    @Override
    public String toString() {
        return name().substring(5);
    }
}
