package com.clare.ben.nab.repository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Collection;
import java.util.Collections;

@Builder
@Getter
@AllArgsConstructor
public class SearchGroceries {
    private String name;
    @Builder.Default
    private Collection<String> tags = Collections.emptyList();
    private String category;
}
