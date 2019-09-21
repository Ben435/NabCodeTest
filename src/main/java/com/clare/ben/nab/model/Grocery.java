package com.clare.ben.nab.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Collections;

@Getter
@AllArgsConstructor
public class Grocery {
    @NotBlank
    private String name;
    @NotBlank
    private String category;
    @NotNull
    private Collection<String> tags;

    // For json mapping.
    public Grocery() {}

    public Grocery(String name, String category) {
        this(name, category, Collections.emptyList());
    }
}
