package com.clare.ben.nab.repository.query;

import com.clare.ben.nab.model.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Collection;
import java.util.Collections;

@Builder
@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class SearchGroceries {
    private String partialName;
    private String category;
    @Builder.Default
    private Collection<Tag> tags = Collections.emptyList();
}
