package com.clare.ben.nab.model;

import lombok.Getter;

import java.util.Collection;
import java.util.Collections;

@Getter
public class Grocery {
    private String name;
    private String category;
    private Collection<String> tags;

    public Grocery(String name, String category) {
        this(name, category, Collections.emptyList());
    }

    public Grocery(String name, String category, Collection<String> tags) {
        this.name = name;
        this.category = category;
        this.tags = tags;
    }
}
