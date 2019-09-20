package com.clare.ben.nab.repository;

import com.clare.ben.nab.model.Grocery;
import com.google.common.base.Preconditions;
import org.springframework.lang.Nullable;
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

    public Collection<Grocery> queryGroceries(@Nullable String name, Collection<String> tags, @Nullable String category) {
        Preconditions.checkNotNull(tags);

        return store
                .values()
                .stream()
                .filter(grocery -> Objects.isNull(name) || grocery.getName().toLowerCase().contains(name.toLowerCase()))
                .filter(grocery -> tags.size() == 0 || grocery.getTags().containsAll(tags))
                .filter(grocery -> Objects.isNull(category) || grocery.getCategory().equalsIgnoreCase(category))
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
