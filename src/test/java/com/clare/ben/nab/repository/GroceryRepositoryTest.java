package com.clare.ben.nab.repository;

import com.clare.ben.nab.model.Grocery;
import com.clare.ben.nab.model.Tag;
import com.clare.ben.nab.repository.query.SearchGroceries;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class GroceryRepositoryTest {
    @InjectMocks
    GroceryRepository repository;

    private Tag mockTag;
    private Grocery dummyGrocery;

    @Before
    public void setup() {
        populateStoreWithMocks();

        mockTag = new Tag("tag");
        dummyGrocery = new Grocery("hello world!", "french", Collections.singleton(mockTag));
    }

    @Test(expected = NullPointerException.class)
    public void searchGroceries_withNullSearchObject_throwsException() {
        repository.searchGroceries(null);
    }

    @Test
    public void searchGroceries_withNoParams_returnsAllItems() {

        SearchGroceries query = SearchGroceries
                .builder()
                .build();

        Collection<Grocery> groceries = repository.searchGroceries(query);

        Assert.assertEquals(10, groceries.size());
    }

    @Test
    public void searchGroceries_withPartialName_returnsMatchingItems() {
        Grocery g = dummyGrocery;
        repository.putGrocery(g);

        SearchGroceries query = SearchGroceries
                .builder()
                .partialName("llo w")
                .build();

        Collection<Grocery> groceries = repository.searchGroceries(query);

        Assert.assertEquals(1, groceries.size());
        Assert.assertTrue(groceries.contains(g));
    }

    @Test
    public void searchGroceries_withCategory_returnsMatchingItems() {
        Grocery g = dummyGrocery;
        repository.putGrocery(g);

        SearchGroceries query = SearchGroceries
                .builder()
                .category("french")
                .build();

        Collection<Grocery> groceries = repository.searchGroceries(query);

        Assert.assertEquals(1, groceries.size());
        Assert.assertTrue(groceries.contains(g));
    }

    @Test
    public void searchGroceries_withTags_returnsMatchingItems() {
        Grocery g = dummyGrocery;
        repository.putGrocery(g);

        SearchGroceries query = SearchGroceries
                .builder()
                .tags(Collections.singletonList(mockTag.getName()))
                .build();

        Collection<Grocery> groceries = repository.searchGroceries(query);

        Assert.assertEquals(1, groceries.size());
        Assert.assertTrue(groceries.contains(g));
    }

    @Test
    public void searchGroceries_withPartialNameAndCategoryAndTags_returnsMatchingItems() {
        Grocery g = dummyGrocery;
        repository.putGrocery(g);

        SearchGroceries query = SearchGroceries
                .builder()
                .partialName("llo")
                .tags(Collections.singletonList(mockTag.getName()))
                .category("french")
                .build();

        Collection<Grocery> groceries = repository.searchGroceries(query);

        Assert.assertEquals(1, groceries.size());
        Assert.assertTrue(groceries.contains(g));
    }

    @Test
    public void searchGroceries_withPartialNameAndCategoryAndTagsButNoMatchingItemInStore_returnsNoItems() {
        Grocery g = dummyGrocery;
        repository.putGrocery(g);

        SearchGroceries query = SearchGroceries
                .builder()
                .partialName("llo")
                .tags(Collections.singletonList("gat"))
                .category("french")
                .build();

        Collection<Grocery> groceries = repository.searchGroceries(query);

        Assert.assertEquals(0, groceries.size());
    }

    @Test
    public void getGrocery_forExistingGrocery_returnsGrocery() {
        Grocery expected = dummyGrocery;
        String id = repository.putGrocery(expected).getId();

        Optional<Grocery> actual = repository.getGrocery(id);

        Assert.assertTrue(actual.isPresent());
        Assert.assertEquals(expected, actual.get());
    }

    @Test
    public void getGrocery_forNonExistingId_returnsEmpty() {
        Grocery expected = dummyGrocery;
        String id = repository.putGrocery(expected).getId();

        Optional<Grocery> actual = repository.getGrocery(id.substring(id.length() / 2));

        Assert.assertTrue(actual.isEmpty());
    }

    @Test
    public void putGrocery_withNoId_generatesIdAndPutsInStore() {
        Grocery expected = dummyGrocery;
        String id = repository.putGrocery(expected).getId();

        Optional<Grocery> actual = repository.getGrocery(id);

        Assert.assertTrue(actual.isPresent());
        Assert.assertEquals(expected, actual.get());
    }

    @Test
    public void deleteGrocery_withExistingId_removesItemAndReturnsRemoved() {
        Grocery expected = dummyGrocery;
        String id = repository.putGrocery(expected).getId();

        Optional<Grocery> actual = repository.deleteGrocery(id);

        Assert.assertTrue(actual.isPresent());
        Assert.assertEquals(expected, actual.get());
    }

    @Test
    public void deleteGrocery_withNonExistingId_returnsEmpty() {
        Grocery expected = dummyGrocery;
        String id = repository.putGrocery(expected).getId();

        Optional<Grocery> actual = repository.deleteGrocery(id.substring(id.length() / 2));

        Assert.assertTrue(actual.isEmpty());
    }

    private void populateStoreWithMocks() {
        String key;
        for (int i = 0; i < 10; i++) {
            key = String.valueOf(i);
            this.repository.putGrocery(new Grocery("tomato_" + key, i % 2 == 0 ? "fruit" : "vegetable"));
        }
    }
}