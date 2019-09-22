package com.clare.ben.nab.service;

import com.clare.ben.nab.repository.GroceryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class GroceryServiceTest {

    @InjectMocks
    private GroceryService service;

    @Mock
    private GroceryRepository repository;

    @Test
    public void searchGroceries() {
    }

    @Test
    public void getGrocery() {
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