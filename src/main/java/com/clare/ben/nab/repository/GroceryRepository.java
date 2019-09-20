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
                .filter(grocery -> Objects.isNull(search.getPartialName()) || grocery.getName().toLowerCase().contains(search.getPartialName().toLowerCase()))
                .filter(grocery -> Objects.isNull(search.getCategory()) || grocery.getCategory().equalsIgnoreCase(search.getCategory()))
                .filter(grocery -> grocery.getTags().containsAll(search.getTags()))
                .collect(Collectors.toUnmodifiableList());
    }

    public Optional<Grocery> getGrocery(String id) {
        Preconditions.checkNotNull(id);

        return Optional.ofNullable(store.get(id));
    }

    public String putGrocery(Grocery grocery) {
        Preconditions.checkNotNull(grocery);

        return this.putGrocery(UUID.randomUUID().toString(), grocery);
    }

    public Optional<Grocery> updateGrocery(String id, Grocery grocery) {
        Preconditions.checkNotNull(id);
        Preconditions.checkNotNull(grocery);

        if (getGrocery(id).isPresent()) {
            putGrocery(id, grocery);

            return Optional.of(grocery);
        } else {
            return Optional.empty();
        }
    }

    private String putGrocery(String id, Grocery grocery) {
        store.put(id, grocery);

        return id;
    }

    public Optional<Grocery> deleteGrocery(String id) {
        Preconditions.checkNotNull(id);

        return Optional.ofNullable(store.remove(id));
    }

}
