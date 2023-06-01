package com.projects.ski_resort_project.model;

import java.io.Serializable;

public interface BaseEntity<T extends Serializable> extends Serializable {
    T getId();
    void setId(T continentId);
}