package com.clare.ben.nab.repository.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class SearchGroceries {
    private String partialName;
    private String category;
}
