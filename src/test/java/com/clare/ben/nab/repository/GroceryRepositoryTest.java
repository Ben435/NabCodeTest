package com.clare.ben.nab.repository;

import com.clare.ben.nab.model.Grocery;
import com.clare.ben.nab.repository.query.SearchGroceries;
import org.hamcrest.beans.SamePropertyValuesAs;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RunWith(MockitoJUnitRunner.class)
public class GroceryRepositoryTest {
    @InjectMocks
    GroceryRepository repository;

    private Grocery dummyGrocery;

    @Before
    public void setup() {
        populateStoreWithMocks();

        dummyGrocery = new Grocery("hello world!", "french");
        dummyGrocery.setId("123");
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
        repository.createGrocery(g);

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
        repository.createGrocery(g);

        SearchGroceries query = SearchGroceries
                .builder()
                .category("french")
                .build();

        Collection<Grocery> groceries = repository.searchGroceries(query);

        Assert.assertEquals(1, groceries.size());
        Assert.assertTrue(groceries.contains(g));
    }

    @Test
    public void searchGroceries_withPartialNameAndCategory_returnsMatchingItems() {
        Grocery g = dummyGrocery;
        repository.createGrocery(g);

        SearchGroceries query = SearchGroceries
                .builder()
                .partialName("llo")
                .category("french")
                .build();

        Collection<Grocery> groceries = repository.searchGroceries(query);

        Assert.assertEquals(1, groceries.size());
        Assert.assertTrue(groceries.contains(g));
    }

    @Test
    public void searchGroceries_withPartialNameAndCategoryButNoMatchingItemInStore_returnsNoItems() {
        Grocery g = dummyGrocery;
        repository.createGrocery(g);

        SearchGroceries query = SearchGroceries
                .builder()
                .partialName("llo")
                .category("unknown_category")
                .build();

        Collection<Grocery> groceries = repository.searchGroceries(query);

        Assert.assertEquals(0, groceries.size());
    }

    @Test
    public void getGrocery_forExistingGrocery_returnsGrocery() {
        Grocery expected = dummyGrocery;
        String id = repository.createGrocery(expected).getId();

        Optional<Grocery> actual = repository.getGrocery(id);

        Assert.assertTrue(actual.isPresent());
        Assert.assertEquals(expected, actual.get());
    }

    @Test
    public void getGrocery_forNonExistingId_returnsEmpty() {
        Grocery expected = dummyGrocery;
        String id = repository.createGrocery(expected).getId();

        Optional<Grocery> actual = repository.getGrocery(id.substring(id.length() / 2));

        Assert.assertTrue(actual.isEmpty());
    }

    @Test
    public void updateGrocery_withValidGrocery_returnsOldItem() {
        Grocery original = dummyGrocery;
        Grocery createdOriginal = repository.createGrocery(original);
        Grocery expected = Grocery.builder().id(createdOriginal.getId()).name("different_name").category("cheese").build();

        Grocery actual = repository.updateGrocery(expected);

        Assert.assertEquals(actual.getId(), original.getId());
        Assert.assertThat(actual, new SamePropertyValuesAs<>(createdOriginal));
    }

    @Test
    public void createGrocery_withNoId_generatesIdAndPutsInStore() {
        Grocery expected = dummyGrocery;
        expected.setId(null);

        Grocery actual = repository.createGrocery(expected);

        Assert.assertEquals(expected, actual);
        Assert.assertNotNull(actual.getId());
    }

    @Test
    public void deleteGrocery_withExistingId_removesItemAndReturnsRemoved() {
        Grocery expected = dummyGrocery;
        String id = repository.createGrocery(expected).getId();

        Optional<Grocery> actual = repository.deleteGrocery(id);

        Assert.assertTrue(actual.isPresent());
        Assert.assertEquals(expected, actual.get());
    }

    @Test
    public void deleteGrocery_withNonExistingId_returnsEmpty() {
        Grocery expected = dummyGrocery;
        String id = repository.createGrocery(expected).getId();

        Optional<Grocery> actual = repository.deleteGrocery(id.substring(id.length() / 2));

        Assert.assertTrue(actual.isEmpty());
    }

    @Test
    public void getAllCategories_withOneCategoryInStore_returnsCategory() {
        repository.clearStore();

        String expected = "hello";
        Grocery singleGrocery = Grocery.builder().name("test").category(expected).build();
        repository.createGrocery(singleGrocery);

        Collection<String> categories = repository.getAllCategories();

        Assert.assertEquals(1, categories.size());
        categories.forEach(actual -> Assert.assertEquals(expected, actual));
    }

    @Test
    public void getAllCategories_withMultipleCategoriesInStore_returnsCategories() {
        String expected = "other";
        Grocery singleGrocery = Grocery.builder().name("test").category(expected).build();
        repository.createGrocery(singleGrocery);

        Collection<String> categories = repository.getAllCategories();

        Assert.assertEquals(3, categories.size());
        List<String> expecteds = new ArrayList<>();
        expecteds.add("fruit");
        expecteds.add("vegetable");
        expecteds.add(expected);

        expecteds.sort(String::compareTo);

        List<String> actuals = categories.stream().sorted().collect(Collectors.toList());

        for (int i=0; i<3; i++) {
            Assert.assertEquals(expecteds.get(i), actuals.get(i));
        }
    }

    private void populateStoreWithMocks() {
        String key;
        for (int i = 0; i < 10; i++) {
            key = String.valueOf(i);
            this.repository.createGrocery(new Grocery("tomato_" + key, i % 2 == 0 ? "fruit" : "vegetable"));
        }
    }
}