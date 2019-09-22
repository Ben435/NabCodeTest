package com.clare.ben.nab.service;

import com.clare.ben.nab.model.Grocery;
import com.clare.ben.nab.repository.GroceryRepository;
import com.clare.ben.nab.repository.query.SearchGroceries;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GroceryServiceTest {

    @InjectMocks
    private GroceryService service;

    @Mock
    private GroceryRepository repository;

    private Grocery dummyGrocery;

    @Captor
    ArgumentCaptor<SearchGroceries> searchCaptor;

    @Before
    public void setup() {
        dummyGrocery = Grocery.builder().name("hello").category("world").build();
    }

    @Test
    public void searchGroceries_withNonNullParams_callsRepositoryAndReturnsResults() {
        Collection<Grocery> expected = Collections.singletonList(dummyGrocery);
        when(repository.searchGroceries(searchCaptor.capture())).thenReturn(expected);
        String expectedName = "hello";
        String expectedCategory = "world";

        Collection<Grocery> actual = service.searchGroceries(expectedName, expectedCategory);

        Assert.assertEquals(expected, actual);

        SearchGroceries search = searchCaptor.getValue();
        Assert.assertEquals(expectedName, search.getPartialName());
        Assert.assertEquals(expectedCategory, search.getCategory());
    }

    @Test
    public void searchGroceries_withNullParams_callsRepositoryAndReturnsResults() {
        Collection<Grocery> expected = Collections.singletonList(dummyGrocery);
        when(repository.searchGroceries(searchCaptor.capture())).thenReturn(expected);

        Collection<Grocery> actual = service.searchGroceries(null, null);

        Assert.assertEquals(expected, actual);

        SearchGroceries search = searchCaptor.getValue();
        Assert.assertNull(search.getPartialName());
        Assert.assertNull(search.getCategory());
    }

    @Test
    public void getGrocery_withValidId_callsRepositoryAndReturnsResults() {
        when(repository.getGrocery(anyString())).thenReturn(Optional.of(dummyGrocery));

        Optional<Grocery> actual = service.getGrocery("123");

        Assert.assertTrue(actual.isPresent());
        Assert.assertEquals(dummyGrocery, actual.get());

        verify(repository).getGrocery("123");
    }

    @Test(expected = NullPointerException.class)
    public void getGrocery_withNullId_throwsException() {
        service.getGrocery(null);
    }

    @Test
    public void createGrocery() {
    }

    @Test
    public void updateGrocery() {
    }

    @Test
    public void deleteGrocery() {
    }
}