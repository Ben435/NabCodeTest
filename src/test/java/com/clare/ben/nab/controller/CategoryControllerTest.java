package com.clare.ben.nab.controller;

import com.clare.ben.nab.service.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@WebMvcTest(CategoryController.class)
public class CategoryControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CategoryService service;

    private ObjectMapper mapper = new ObjectMapper();

    private static final String API_PREFIX = "/api/category";

    @Test
    public void getAllCategories_whenCalled_withReturnAllCategories() throws Exception {
        String expected = "hello!";
        when(service.getAllCategories()).thenReturn(Collections.singletonList(expected));

        mvc.perform(get(API_PREFIX)).andExpect(resp -> {
            String[] categories = mapper
                    .readerFor(String[].class)
                    .readValue(resp.getResponse().getContentAsString());

            assertEquals(1, categories.length);
            assertEquals(expected, categories[0]);
        });
    }
}