package com.ezgroceries.shoppinglist.controller.cocktail;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ActiveProfiles({"hsqldb"})
class CocktailControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void testSearchCocktail() throws Exception {
        mockMvc.perform(get("/cocktails")
                .param("search", "Russian")
                .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$..cocktailId").exists())
                .andExpect(jsonPath("$..name").exists())
                .andExpect(jsonPath("$..glass").exists())
                .andExpect(jsonPath("$..instructions").exists())
                .andExpect(jsonPath("$..image").exists())
                .andExpect(jsonPath("$..ingredients").exists());
    }
}
