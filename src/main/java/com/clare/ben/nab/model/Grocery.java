package com.clare.ben.nab.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

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

    public Grocery(@NotBlank String name, @NotBlank String category) {
        this.name = name;
        this.category = category;
    }
}
