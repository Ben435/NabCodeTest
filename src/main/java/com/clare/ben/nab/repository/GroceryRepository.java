package com.clare.ben.nab.repository;

import com.clare.ben.nab.model.Grocery;
import com.google.common.base.Preconditions;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Emulating some field-queryable database.
 * Eg: SQL, document based
 * */
@Repository
public class GroceryRepository {

    private Map<String, Grocery> store;

    public GroceryRepository() {
        store = new HashMap<>();
    }

    public Collection<Grocery> searchGroceries(SearchGroceries search) {
        Preconditions.checkNotNull(search);

        return store
                .values()
                .stream()
                .filter(grocery -> Objects.isNull(search.getName()) || grocery.getName().toLowerCase().contains(search.getName().toLowerCase()))
                .filter(grocery -> search.getTags().size() == 0 || grocery.getTags().containsAll(search.getTags()))
                .filter(grocery -> Objects.isNull(search.getCategory()) || grocery.getCategory().equalsIgnoreCase(search.getCategory()))
                .collect(Collectors.toUnmodifiableList());
    }

    public Optional<Grocery> getGrocery(String id) {
        return Optional.ofNullable(store.get(id));
    }

    public String putGrocery(Grocery grocery) {
        return this.putGrocery(UUID.randomUUID().toString(), grocery);
    }

    private String putGrocery(String id, Grocery grocery) {
        store.put(id, grocery);

        return id;
    }

    public Optional<Grocery> removeGrocery(String id) {
        return Optional.ofNullable(store.remove(id));
    }

}
