package com.clare.ben.nab.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Grocery {
    @NotBlank
    private String id;

    @NotBlank
    private String name;
    @NotBlank
    private String category;

    @NotNull
    private Set<Tag> tags;

    public Grocery(String name, String category) {
        this(name, category, Collections.emptySet());
    }

    public Grocery(@NotBlank String name, @NotBlank String category, @NotNull Set<Tag> tags) {
        this.name = name;
        this.category = category;
        this.tags = tags;
    }

    public void addTag(Tag tag) {
        tags.add(tag);
    }
}
