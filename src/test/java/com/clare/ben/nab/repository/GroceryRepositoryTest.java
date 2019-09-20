package com.clare.ben.nab.repository;

import com.clare.ben.nab.model.Grocery;
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

    @Before
    public void setup() {
        populateStoreWithMocks();
    }

    @Test(expected = NullPointerException.class)
    public void queryGroceries_withNullTags_throwsException() {
        repository.queryGroceries("", null, null);
    }

    @Test
    public void queryGroceries_withNoParams_returnsAllItems() {
        Collection<Grocery> groceries = repository.queryGroceries("", Collections.emptyList(), null);

        Assert.assertEquals(10, groceries.size());
    }

    @Test
    public void queryGroceries_withPartialName_returnsMatchingItems() {
        Grocery g = new Grocery("hello world!", "french");
        repository.putGrocery(g);

        Collection<Grocery> groceries = repository.queryGroceries("llo w", Collections.emptyList(), null);

        Assert.assertEquals(1, groceries.size());
        Assert.assertTrue(groceries.contains(g));
    }

    @Test
    public void queryGroceries_withCategory_returnsMatchingItems() {
        Grocery g = new Grocery("hello world!", "french");
        repository.putGrocery(g);

        Collection<Grocery> groceries = repository.queryGroceries("", Collections.emptyList(), "french");

        Assert.assertEquals(1, groceries.size());
        Assert.assertTrue(groceries.contains(g));
    }

    @Test
    public void queryGroceries_withTags_returnsMatchingItems() {
        Grocery g = new Grocery("hello world!", "french", Collections.singletonList("tag"));
        repository.putGrocery(g);

        Collection<Grocery> groceries = repository.queryGroceries(null, Collections.singletonList("tag"), null);

        Assert.assertEquals(1, groceries.size());
        Assert.assertTrue(groceries.contains(g));
    }

    @Test
    public void queryGroceries_withPartialNameAndCategoryAndTags_returnsMatchingItems() {
        Grocery g = new Grocery("hello world!", "french", Collections.singletonList("tag"));
        repository.putGrocery(g);

        Collection<Grocery> groceries = repository.queryGroceries("llo", Collections.singletonList("tag"), "french");

        Assert.assertEquals(1, groceries.size());
        Assert.assertTrue(groceries.contains(g));
    }

    @Test
    public void queryGroceries_withPartialNameAndCategoryAndTagsButNoMatchingItemInStore_returnsNoItems() {
        Grocery g = new Grocery("hello world!", "french", Collections.singletonList("tag"));
        repository.putGrocery(g);

        Collection<Grocery> groceries = repository.queryGroceries("llo", Collections.singletonList("gat"), "french");

        Assert.assertEquals(0, groceries.size());
    }

    @Test
    public void getGrocery_forExistingGrocery_returnsGrocery() {
        Grocery expected = new Grocery("hello world!", "french", Collections.singletonList("tag"));
        String id = repository.putGrocery(expected);

        Optional<Grocery> actual = repository.getGrocery(id);

        Assert.assertTrue(actual.isPresent());
        Assert.assertEquals(expected, actual.get());
    }

    @Test
    public void getGrocery_forNonExistingId_returnsEmpty() {
        Grocery expected = new Grocery("hello world!", "french", Collections.singletonList("tag"));
        String id = repository.putGrocery(expected);

        Optional<Grocery> actual = repository.getGrocery(id.substring(id.length() / 2));

        Assert.assertTrue(actual.isEmpty());
    }

    @Test
    public void putGrocery_withNoId_generatesIdAndPutsInStore() {
        Grocery expected = new Grocery("hello world!", "french", Collections.singletonList("tag"));
        String id = repository.putGrocery(expected);

        Optional<Grocery> actual = repository.getGrocery(id);

        Assert.assertTrue(actual.isPresent());
        Assert.assertEquals(expected, actual.get());
    }

    @Test
    public void removeGrocery_withExistingId_removesItemAndReturnsRemoved() {
        Grocery expected = new Grocery("hello world!", "french", Collections.singletonList("tag"));
        String id = repository.putGrocery(expected);

        Optional<Grocery> actual = repository.removeGrocery(id);

        Assert.assertTrue(actual.isPresent());
        Assert.assertEquals(expected, actual.get());
    }

    @Test
    public void removeGrocery_withNonExistingId_returnsEmpty() {
        Grocery expected = new Grocery("hello world!", "french", Collections.singletonList("tag"));
        String id = repository.putGrocery(expected);

        Optional<Grocery> actual = repository.removeGrocery(id.substring(id.length() / 2));

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