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
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RunWith(SpringRunner.class)
@WebMvcTest(GroceryController.class)
public class GroceryControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private GroceryService service;

    private ObjectMapper mapper = new ObjectMapper();

    private static final String API_PREFIX = "/api/grocery";

    @Test
    public void searchGroceries_withValidParams_callsServiceAndReturnsSearchResults() throws Exception {
        Grocery expected = validDummyGrocery();
        when(service.searchGroceries(any(), any())).thenReturn(Collections.singletonList(expected));

        mvc.perform(get(API_PREFIX).param("name", "mat"))
                .andExpect(res -> {
                    Grocery[] actual = mapper.readerFor(Grocery[].class).readValue(res.getResponse().getContentAsString());

                    assertThat(actual[0], new SamePropertyValuesAs<>(expected));

                    verify(service).searchGroceries(eq("mat"), isNull());
                });
    }

    @Test
    public void createGrocery_withValidGrocery_callsServiceAndReturnsId() throws Exception {
        Grocery expected = validDummyGrocery();
        when(service.createGrocery(any())).thenReturn(expected);

        CreateGroceryRequest req = CreateGroceryRequest.builder().name(expected.getName()).category(expected.getCategory()).build();

        mvc.perform(
                post(API_PREFIX)
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
                post(API_PREFIX)
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
    public void getGrocery_withValidId_returnsObject() throws Exception {
        Grocery expected = validDummyGrocery();

        when(service.getGrocery(expected.getId())).thenReturn(Optional.of(expected));

        mvc.perform(
                get(API_PREFIX + "/{id}", expected.getId())
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(res -> {
                    Grocery actual = mapper.readerFor(Grocery.class).readValue(res.getResponse().getContentAsString());
                    assertThat(actual, new SamePropertyValuesAs<>(expected));

                    verify(service).getGrocery(expected.getId());
                });
    }

    @Test
    public void getGrocery_withInvalidId_returnsNotFound() throws Exception {
        String dummyId = "nope";
        when(service.getGrocery(dummyId)).thenReturn(Optional.empty());

        mvc.perform(
                get(API_PREFIX + "/{id}", dummyId)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(res -> {
                    assertEquals(404, res.getResponse().getStatus());
                    verify(service).getGrocery(dummyId);
                });
    }

    @Test
    public void editGrocery_withValidIdAndUpdatedObject_updatesObjectAndReturnsNewVersion() throws Exception {
        Grocery expected = validDummyGrocery();

        when(service.updateGrocery(any(Grocery.class))).thenReturn(Optional.of(expected));

        mvc.perform(
                put(API_PREFIX)
                        .content(mapper.writeValueAsString(expected))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(res -> {
                    assertEquals(204, res.getResponse().getStatus());

                    verify(service).updateGrocery(any(Grocery.class));
                });
    }

    @Test
    public void editGrocery_withInvalidId_returnsNotFound() throws Exception {
        Grocery expected = validDummyGrocery();

        when(service.updateGrocery(any(Grocery.class))).thenReturn(Optional.empty());

        mvc.perform(
                put(API_PREFIX)
                        .content(mapper.writeValueAsString(expected))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(res -> {
                    assertEquals(404, res.getResponse().getStatus());

                    verify(service).updateGrocery(any(Grocery.class));
                });
    }

    @Test
    public void editGrocery_withInvalidUpdatedObject_throwsValidationError() throws Exception {
        Grocery dummy = validDummyGrocery();

        dummy.setName("");

        mvc.perform(
                put(API_PREFIX)
                        .content(mapper.writeValueAsString(dummy))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(res -> {
                    assertNotNull(res.getResolvedException());
                    assertEquals(MethodArgumentNotValidException.class, res.getResolvedException().getClass());

                    verify(service, never()).updateGrocery(any(Grocery.class));
                });
    }

    @Test
    public void deleteGrocery_withValidId_deletesObjectAndReturnsObject() throws Exception {
        Grocery expected = validDummyGrocery();

        when(service.deleteGrocery(expected.getId())).thenReturn(Optional.of(expected));

        mvc.perform(
                delete(API_PREFIX + "/{id}", expected.getId())
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(res -> {
                    Grocery actual = mapper.readerFor(Grocery.class).readValue(res.getResponse().getContentAsString());
                    assertThat(actual, new SamePropertyValuesAs<>(expected));

                    verify(service).deleteGrocery(expected.getId());
                });
    }

    @Test
    public void deleteGrocery_withInvalidId_returnsNotFound() throws Exception {
        String dummyId = "nope";
        when(service.deleteGrocery(dummyId)).thenReturn(Optional.empty());

        mvc.perform(
                delete(API_PREFIX + "/{id}", dummyId)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(res -> {
                    assertEquals(404, res.getResponse().getStatus());

                    verify(service).deleteGrocery(any());
                });
    }

    @Test
    public void bootstrapGroceries_whenCalled_createsSomeDemoGroceries() throws Exception {
        when(service.createGrocery(any())).thenReturn(Grocery.builder().build());

        mvc.perform(
                put(API_PREFIX + "/bootstrap"))
                .andExpect(res -> {
                    assertEquals(200, res.getResponse().getStatus());

                    verify(service, times(100)).createGrocery(any());
                });
    }

    private Grocery validDummyGrocery() {
        Grocery g = new Grocery("tomato", "vegetable");
        g.setId("1234");

        return g;
    }
}