package com.clare.ben.nab.service;

import com.clare.ben.nab.repository.GroceryRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collection;
import java.util.Collections;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CategoryServiceTest {

    @InjectMocks
    private CategoryService service;

    @Mock
    private GroceryRepository repository;

    @Test
    public void getAllCategories_whenCalled_callsRepositoryMethod() {
        Collection<String> expected = Collections.singletonList("hello");
        when(repository.getAllCategories()).thenReturn(expected);

        Collection<String> actual = service.getAllCategories();

        Assert.assertEquals(expected, actual);
        verify(repository).getAllCategories();
    }
}