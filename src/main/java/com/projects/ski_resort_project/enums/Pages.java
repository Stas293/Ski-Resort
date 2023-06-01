package com.projects.ski_resort_project.enums;

public enum Pages {
    ADMIN_CITY_PAGE("admin/city"),
    ADMIN_COUNTRY_PAGE("admin/country"),
    ADMIN_EVENT_PAGE("admin/event"),
    ADMIN_USER_LIST_PAGE("admin/users"),
    ADMIN_USER_PAGE("admin/user"),
    ADMIN_NEWS_PAGE("admin/news"),
    ADMIN_RESORT_PAGE("admin/resort"),
    AUTH_LOGIN_PAGE("auth/login"),
    AUTH_REGISTRATION_PAGE("auth/registration"),
    EUROPE_PAGE("resorts/europe"),
    EVENTS_ARTICLE_PAGE("events/article"),
    EVENTS_PAGE("events/events"),
    EVENTS_REDIRECT_PAGE("redirect:/events"),
    INDEX_PAGE("index"),
    NEWS_ARTICLE_PAGE("news/article"),
    NEWS_PAGE("news/news"),
    NEWS_REDIRECT("redirect:/news"),
    NORTH_AMERICA_PAGE("resorts/north_america"),
    OTHER_WORLD_PAGE("resorts/other_world"),
    RESORT_ARTICLE_PAGE("resorts/article"),
    RESORT_REDIRECT("redirect:/resorts"),
    SOUTH_AMERICA_PAGE("resorts/south_america");

    private final String value;

    Pages(String value) {
        this.value = value;
    }

    public String getPage() {
        return value;
    }
}
