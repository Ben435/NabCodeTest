package com.clare.ben.nab.controller;

import com.clare.ben.nab.model.Grocery;
import com.clare.ben.nab.service.GroceryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@WebMvcTest(GroceryController.class)
public class GroceryControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private GroceryService service;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void searchGroceries() {
    }

    @Test
    public void createGrocery_withValidGrocery_callsServiceAndReturnsId() throws Exception {
        String expectedId = "1234";
        when(service.createGrocery(any())).thenReturn(expectedId);

        mvc.perform(
                post("/api/grocery")
                        .content(mapper.writeValueAsBytes(new Grocery("tomato", "vegetable")))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(res -> {
                    assertEquals(expectedId, res.getResponse().getContentAsString());
                    verify(service, only()).createGrocery(any());
                });

    }

    @Test
    public void createGrocery_withInvalidGrocery_throwsValidationError() throws Exception {
        String expectedId = "1234";
        when(service.createGrocery(any())).thenReturn(expectedId);

        mvc.perform(
                post("/api/grocery")
                        .content(mapper.writeValueAsBytes(new Grocery("", "", null)))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(res -> {
                    assertNotNull(res.getResolvedException());
                    assertEquals(MethodArgumentNotValidException.class, res.getResolvedException().getClass());

                    verify(service, never()).createGrocery(any());
                });
    }

    @Test
    public void getGrocery() {
    }

    @Test
    public void editGrocery() {
    }

    @Test
    public void deleteGrocery() {
    }
}