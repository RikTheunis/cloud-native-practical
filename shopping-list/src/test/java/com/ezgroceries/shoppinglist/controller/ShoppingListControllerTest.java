package com.ezgroceries.shoppinglist.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ezgroceries.shoppinglist.contract.AddCocktailToShoppingListInputContract;
import com.ezgroceries.shoppinglist.contract.CreateNewShoppingListInputContract;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;
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

    @Test
    void testAddCocktailToShoppingList() throws Exception {
        AddCocktailToShoppingListInputContract input[] = {
                new AddCocktailToShoppingListInputContract(),
                new AddCocktailToShoppingListInputContract()
        };
        input[0].setCocktailId(UUID.fromString("23b3d85a-3928-41c0-a533-6538a71e17c4"));
        input[1].setCocktailId(UUID.fromString("d615ec78-fe93-467b-8d26-5d26d8eab073"));

        mockMvc.perform(post("/shopping-lists/{shoppingListId}/cocktails", "97c8e5bd-5353-426e-b57b-69eb2260ace3")
                .content(objectMapper.writeValueAsString(input))
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$..cocktailId").exists());
    }

}
