package com.clare.ben.nab.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collection;
import java.util.Collections;

@Getter
@AllArgsConstructor
public class Grocery {
    private String name;
    private String category;
    private Collection<String> tags;

    public Grocery(String name, String category) {
        this(name, category, Collections.emptyList());
    }
}
