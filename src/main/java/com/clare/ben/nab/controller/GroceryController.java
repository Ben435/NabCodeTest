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

    @PostMapping
    public String createGrocery(@RequestBody Grocery req) {
        return service.createGrocery(req);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Grocery> getGrocery(@PathVariable String id) {
        return service.getGrocery(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editGrocery(@PathVariable String id, @RequestBody Grocery req) {
        return service
                .updateGrocery(id, req)
                .map(g -> ResponseEntity.noContent().build())
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Grocery> deleteGrocery(@PathVariable String id) {
        return service
                .deleteGrocery(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
