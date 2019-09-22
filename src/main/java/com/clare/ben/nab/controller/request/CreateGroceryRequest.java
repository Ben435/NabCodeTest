package com.clare.ben.nab.controller.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreateGroceryRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String category;
}
