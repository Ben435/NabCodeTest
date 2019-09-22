package com.clare.ben.nab.repository;

import com.clare.ben.nab.model.Grocery;
import com.clare.ben.nab.model.Tag;
import com.clare.ben.nab.repository.query.SearchGroceries;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.springframework.stereotype.Repository;

import javax.validation.Valid;
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
                .filter(grocery -> {
                    List<String> currentItemTags = grocery.getTags().stream().map(Tag::getName).collect(Collectors.toList());
                    List<String> searchTags = search.getTags().stream().map(Tag::getName).collect(Collectors.toList());
                    return currentItemTags.containsAll(searchTags);
                })
                .collect(Collectors.toUnmodifiableList());
    }

    public Optional<Grocery> getGrocery(String id) {
        Preconditions.checkNotNull(id);

        return Optional.ofNullable(store.get(id));
    }

    public Grocery updateGrocery(Grocery grocery) {
        Preconditions.checkNotNull(grocery);

        return writeGrocery(grocery);
    }

    public Grocery createGrocery(Grocery grocery) {
        Preconditions.checkNotNull(grocery);

        if (Strings.isNullOrEmpty(grocery.getId())) {
            grocery.setId(UUID.randomUUID().toString());
        }

        return writeGrocery(grocery);
    }

    public Optional<Grocery> deleteGrocery(String id) {
        Preconditions.checkNotNull(id);

        return Optional.ofNullable(store.remove(id));
    }

    private Grocery writeGrocery(@Valid Grocery grocery) {
        store.put(grocery.getId(), grocery);

        return grocery;
    }
}
