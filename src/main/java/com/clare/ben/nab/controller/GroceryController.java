package com.clare.ben.nab.controller;

import com.clare.ben.nab.model.Grocery;
import com.clare.ben.nab.service.GroceryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/grocery")
public class GroceryController {

    private final GroceryService service;

    @Autowired
    public GroceryController(GroceryService groceryService) {
        this.service = groceryService;
    }

    @GetMapping
    public Collection<Grocery> searchGroceries(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "tag", required = false) Collection<String> tags
    ) {
        return service.searchGroceries(name, category, tags);
    }

    @GetMapping("/{id}")
    public Grocery getGrocery(@PathVariable String id) {
        return null;
    }

    @PostMapping("/{id}")
    public ResponseEntity<Void> createGrocery(@PathVariable String id, @RequestBody Grocery req) {
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editGrocery(@PathVariable String id, @RequestBody Grocery req) {
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGrocery(@PathVariable String id) {
        return ResponseEntity.noContent().build();
    }
}
