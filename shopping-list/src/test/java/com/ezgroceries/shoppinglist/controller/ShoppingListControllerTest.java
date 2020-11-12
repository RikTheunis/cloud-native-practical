package com.ezgroceries.shoppinglist.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ezgroceries.shoppinglist.contract.CreateNewShoppingListInputContract;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc
class ShoppingListControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void testCreateShoppingList() throws Exception {
        CreateNewShoppingListInputContract inputContract = new CreateNewShoppingListInputContract();
        inputContract.setName("Stephanie's birthday");

        mockMvc.perform(post("/shopping-lists")
                .content(objectMapper.writeValueAsString(inputContract))
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("shoppingListId").exists())
                .andExpect(jsonPath("name").exists());
    }

}
