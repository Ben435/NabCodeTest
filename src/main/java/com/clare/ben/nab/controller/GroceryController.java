package com.clare.ben.nab.controller;

import com.clare.ben.nab.controller.request.CreateGroceryRequest;
import com.clare.ben.nab.controller.request.EditGroceryRequest;
import com.clare.ben.nab.model.Grocery;
import com.clare.ben.nab.service.GroceryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/grocery")
public class GroceryController extends AbstractApiController {

    private final GroceryService service;

    @Autowired
    public GroceryController(GroceryService groceryService) {
        this.service = groceryService;
    }

    @GetMapping
    public Collection<Grocery> searchGroceries(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "tag", required = false) Collection<String> tags,
            @RequestParam(value = "category", required = false) String category
    ) {
        return null;
    }

    @GetMapping("/{id}")
    public Grocery getGrocery(@PathVariable String id) {
        return null;
    }

    @PostMapping("/{id}")
    public ResponseEntity<Void> createGrocery(@PathVariable String id, @RequestBody CreateGroceryRequest req) {
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}")
    public ResponseEntity<Void> editGrocery(@PathVariable String id, @RequestBody EditGroceryRequest req) {
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGrocery(@PathVariable String id) {
        return ResponseEntity.noContent().build();
    }
}
