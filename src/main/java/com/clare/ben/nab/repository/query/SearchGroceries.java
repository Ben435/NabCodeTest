package com.clare.ben.nab.repository.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class SearchGroceries {
    private String partialName;
    private String category;
}
