package com.projects.ski_resort_project.enums;

public enum Defaults {
    DEFAULT_PAGE_NUMBER(0),
    DEFAULT_PAGE_SIZE(3),
    SPACE(" "),;

    private final Object value;

    Defaults(Object value) {
        this.value = value;
    }

    @SuppressWarnings("unchecked")
    public <T> T getValue() {
        Class<T> type = (Class<T>) value.getClass();
        return type.cast(value);
    }
}
