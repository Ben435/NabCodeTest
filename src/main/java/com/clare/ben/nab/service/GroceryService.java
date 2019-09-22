package com.clare.ben.nab.service;

import com.clare.ben.nab.controller.request.CreateGroceryRequest;
import com.clare.ben.nab.model.Grocery;
import com.clare.ben.nab.repository.GroceryRepository;
import com.clare.ben.nab.repository.query.SearchGroceries;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class GroceryService {
    private final GroceryRepository groceryRepository;

    @Autowired
    public GroceryService(GroceryRepository groceryRepository) {
        this.groceryRepository = groceryRepository;
    }

    public Collection<Grocery> searchGroceries(String name, String category) {
        SearchGroceries query = SearchGroceries
                .builder()
                .partialName(name)
                .category(category)
                .build();

        return groceryRepository.searchGroceries(query);
    }

    public Optional<Grocery> getGrocery(String id) {
        Preconditions.checkNotNull(id);

        return groceryRepository.getGrocery(id);
    }

    public Grocery createGrocery(CreateGroceryRequest req) {
        Preconditions.checkNotNull(req);

        return groceryRepository.createGrocery(Grocery
                .builder()
                .name(req.getName())
                .category(req.getCategory())
                .build()
        );
    }

    public Optional<Grocery> updateGrocery(Grocery newGrocery) {
        return groceryRepository
                .getGrocery(newGrocery.getId())
                .map(old -> newGrocery) // Not doing anything fancy, just "if exists, replace".
                .map(groceryRepository::updateGrocery);
    }

    public Optional<Grocery> deleteGrocery(String id) {
        Preconditions.checkNotNull(id);

        return groceryRepository.deleteGrocery(id);
    }
}
