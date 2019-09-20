package com.clare.ben.nab.service;

import com.clare.ben.nab.model.Grocery;
import com.clare.ben.nab.repository.GroceryRepository;
import com.clare.ben.nab.repository.SearchGroceries;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

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

        SearchGroceries query = SearchGroceries
                .builder()
                .partialName(name)
                .category(category)
                .tags(tags)
                .build();

        return groceryRepository.searchGroceries(query);
    }

    public Optional<Grocery> getGrocery(String id) {
        Preconditions.checkNotNull(id);

        return groceryRepository.getGrocery(id);
    }

    public String createGrocery(Grocery grocery) {
        return groceryRepository.putGrocery(grocery);
    }

    public Optional<Grocery> updateGrocery(String id, Grocery grocery) {
        return groceryRepository.updateGrocery(id, grocery);
    }

    public Optional<Grocery> deleteGrocery(String id) {
        Preconditions.checkNotNull(id);

        return groceryRepository.deleteGrocery(id);
    }
}
