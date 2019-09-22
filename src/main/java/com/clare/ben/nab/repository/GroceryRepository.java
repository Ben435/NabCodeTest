package com.clare.ben.nab.repository;

import com.clare.ben.nab.model.Grocery;
import com.clare.ben.nab.repository.query.SearchGroceries;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.springframework.stereotype.Repository;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

/**
 * Emulating a field-queryable database.
 * Eg: SQL or document based
 * */
@Repository
public class GroceryRepository {

    private ConcurrentMap<String, Grocery> store;

    public GroceryRepository() {
        store = new ConcurrentHashMap<>();
    }

    public Collection<Grocery> searchGroceries(SearchGroceries search) {
        Preconditions.checkNotNull(search);

        return store
                .values()
                .stream()
                .filter(grocery -> Objects.isNull(search.getPartialName()) || grocery.getName().toLowerCase().contains(search.getPartialName().toLowerCase()))
                .filter(grocery -> Objects.isNull(search.getCategory()) || grocery.getCategory().equalsIgnoreCase(search.getCategory()))
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

    // This would probably be a separate entity in a traditional data store.
    // However, as its all in memory, just put it here.
    public Collection<String> getAllCategories() {
        return store
                .values()
                .stream()
                .map(Grocery::getCategory)
                .distinct()
                .collect(Collectors.toList());
    }

    void clearStore() {
        this.store.clear();
    }
}
