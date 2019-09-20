package com.clare.ben.nab.service;

import com.clare.ben.nab.model.Grocery;
import com.clare.ben.nab.repository.GroceryRepository;
import com.clare.ben.nab.repository.SearchGroceries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class GroceryService {
    private final GroceryRepository groceryRepository;

    @Autowired
    public GroceryService(GroceryRepository groceryRepository) {
        this.groceryRepository = groceryRepository;
    }

    public Collection<Grocery> searchGroceries(String name, String category, Collection<String> tags) {
        SearchGroceries query = SearchGroceries
                .builder()
                .name(name)
                .category(category)
                .tags(tags)
                .build();

        return groceryRepository.searchGroceries(query);
    }
}
