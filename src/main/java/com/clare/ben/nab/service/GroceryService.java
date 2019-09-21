package com.clare.ben.nab.service;

import com.clare.ben.nab.controller.request.CreateGroceryRequest;
import com.clare.ben.nab.model.Grocery;
import com.clare.ben.nab.model.Tag;
import com.clare.ben.nab.repository.GroceryRepository;
import com.clare.ben.nab.repository.query.SearchGroceries;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GroceryService {
    private final GroceryRepository groceryRepository;

    @Autowired
    public GroceryService(GroceryRepository groceryRepository) {
        this.groceryRepository = groceryRepository;
    }

    public Collection<Grocery> searchGroceries(String name, String category, Collection<String> tags) {
        if (Objects.isNull(tags)) {
            tags = Collections.emptyList();
        }

        Collection<Tag> objectTags = tags.stream().map(Tag::new).collect(Collectors.toList());

        SearchGroceries query = SearchGroceries
                .builder()
                .partialName(name)
                .category(category)
                .tags(objectTags)
                .build();

        return groceryRepository.searchGroceries(query);
    }

    public Optional<Grocery> getGrocery(String id) {
        Preconditions.checkNotNull(id);

        return groceryRepository.getGrocery(id);
    }

    public Grocery createGrocery(CreateGroceryRequest grocery) {
        return groceryRepository.putGrocery(Grocery
                .builder()
                .name(grocery.getName())
                .category(grocery.getCategory())
                .tags(grocery.getTags())
                .build()
        );
    }

    public Optional<Grocery> updateGrocery(Grocery newGrocery) {
        return groceryRepository
                .getGrocery(newGrocery.getId())
                .map(old -> newGrocery) // Not doing anything fancy, just "if exists, replace".
                .map(groceryRepository::putGrocery);
    }

    public Optional<Grocery> deleteGrocery(String id) {
        Preconditions.checkNotNull(id);

        return groceryRepository.deleteGrocery(id);
    }
}
