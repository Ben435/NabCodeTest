package com.clare.ben.nab.controller;

import com.clare.ben.nab.controller.request.CreateGroceryRequest;
import com.clare.ben.nab.model.Grocery;
import com.clare.ben.nab.service.GroceryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.beans.SamePropertyValuesAs;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Collections;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    public void searchGroceries_withValidParams_callsServiceAndReturnsSearchResults() throws Exception {
        Grocery expected = validDummyGrocery();
        when(service.searchGroceries(any(), any(), any())).thenReturn(Collections.singletonList(expected));

        mvc.perform(get("/api/grocery").param("name", "mat"))
                .andExpect(res -> {
                    Grocery[] actual = mapper.readerFor(Grocery[].class).readValue(res.getResponse().getContentAsString());

                    assertThat(actual[0], new SamePropertyValuesAs<>(expected));

                    verify(service).searchGroceries(eq("mat"), isNull(), isNull());
                });
    }

    @Test
    public void createGrocery_withValidGrocery_callsServiceAndReturnsId() throws Exception {
        Grocery expected = validDummyGrocery();
        expected.setId("1234");
        when(service.createGrocery(any())).thenReturn(expected);

        CreateGroceryRequest req = CreateGroceryRequest.builder().name(expected.getName()).category(expected.getCategory()).tags(expected.getTags()).build();

        mvc.perform(
                post("/api/grocery")
                        .content(mapper.writeValueAsBytes(req))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(res -> {
                    Grocery actual = mapper.readerFor(Grocery.class).readValue(res.getResponse().getContentAsString());
                    assertThat(actual, new SamePropertyValuesAs<>(expected));
                    verify(service, only()).createGrocery(any());
                });
    }

    @Test
    public void createGrocery_withInvalidGrocery_throwsValidationError() throws Exception {
        CreateGroceryRequest req = CreateGroceryRequest.builder().build();

        mvc.perform(
                post("/api/grocery")
                        .content(mapper.writeValueAsBytes(req))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(res -> {
                    assertNotNull(res.getResolvedException());
                    assertEquals(MethodArgumentNotValidException.class, res.getResolvedException().getClass());

                    verify(service, never()).createGrocery(any());
                });
    }

    @Test
    public void getGrocery_withValidId_returnsObject() {

    }

    @Test
    public void editGrocery() {
    }

    @Test
    public void deleteGrocery() {
    }

    private Grocery validDummyGrocery() {
        return new Grocery("tomato", "vegetable");
    }
}