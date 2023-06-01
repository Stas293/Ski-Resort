package com.projects.ski_resort_project.enums;

public enum Links {
    MAIN_PAGE("/"),
    RESORTS_PAGE("/resorts"),
    EVENTS_PAGE("/events"),
    REDIRECT_REGISTRATION_PAGE("redirect:/registration"),
    REDIRECT_LOGIN_PAGE("redirect:/login"),
    ADMIN_PAGES("/admin/**"),
    USER_PAGES("/user/**"),
    LOGOUT("/logout"),
    LOGIN("/login");


    private final String value;

    Links(String value) {
        this.value = value;
    }

    public String getLink() {
        return value;
    }
}
