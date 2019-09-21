package com.clare.ben.nab.controller.request;

import com.clare.ben.nab.model.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreateGroceryRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String category;

    @NotNull
    private Set<Tag> tags;
}
