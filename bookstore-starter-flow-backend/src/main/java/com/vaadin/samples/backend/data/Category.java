package com.vaadin.samples.backend.data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class Category implements Serializable {

    @NotNull
    private int id;
    @NotNull
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return getName();
    }
}
