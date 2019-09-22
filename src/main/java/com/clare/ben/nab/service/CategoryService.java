package com.clare.ben.nab.service;

import com.clare.ben.nab.repository.GroceryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class CategoryService {
    private final GroceryRepository groceryRepository;

    @Autowired
    public CategoryService(GroceryRepository groceryRepository) {
        this.groceryRepository = groceryRepository;
    }

    public Collection<String> getAllCategories() {
        return groceryRepository.getAllCategories();
    }
}
