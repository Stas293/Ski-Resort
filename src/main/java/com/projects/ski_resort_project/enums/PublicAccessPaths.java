package com.projects.ski_resort_project.enums;

public enum PublicAccessPaths {
    HOME("/"),
    CSS("/css/**"),
    JS("/js/**"),
    LOGIN("/login"),
    REGISTRATION("/registration"),
    CONTACT_US("/contact_us"),
    RESET("/reset"),
    RESET_PASSWORD("/reset-password"),
    NEWS("/news/**"),
    EVENTS("/events/**"),
    RESORTS("/resorts/**"),
    NEWS_API("/api/v1/news/**"),
    EVENTS_API("/api/v1/events/**"),
    RESORTS_API("/api/v1/resorts/**"),
    FEEDBACK_API("/api/v1/feedback/**"),
    ERROR("/error"),
    API_DOCS("/v3/api-docs/**"),
    SWAGGER_UI("/swagger-ui/**");

    private final String path;

    PublicAccessPaths(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    @Override
    public String toString() {
        return this.path;
    }
}
