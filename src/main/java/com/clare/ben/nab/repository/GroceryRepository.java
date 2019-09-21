package com.clare.ben.nab.repository;

import com.clare.ben.nab.model.Grocery;
import com.clare.ben.nab.repository.query.SearchGroceries;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Emulating a field-queryable database.
 * Eg: SQL or document based
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

    public Grocery putGrocery(Grocery grocery) {
        Preconditions.checkNotNull(grocery);

        if (Strings.isNullOrEmpty(grocery.getId())) {
            grocery.setId(UUID.randomUUID().toString());
        }

        return store.put(grocery.getId(), grocery);
    }

    public Optional<Grocery> deleteGrocery(String id) {
        Preconditions.checkNotNull(id);

        return Optional.ofNullable(store.remove(id));
    }
}
