package com.clare.ben.nab.controller.request;

import com.clare.ben.nab.model.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class EditGroceryRequest {
    private String name;
    private String category;
    private Set<Tag> tags;
}
