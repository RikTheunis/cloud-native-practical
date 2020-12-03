package com.ezgroceries.shoppinglist.controller.shoppinglist;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ezgroceries.shoppinglist.controller.shoppinglist.contract.AddCocktailToShoppingListInputContract;
import com.ezgroceries.shoppinglist.controller.shoppinglist.contract.CreateNewShoppingListInputContract;
import com.ezgroceries.shoppinglist.service.cocktail.model.CocktailResource;
import com.ezgroceries.shoppinglist.service.shoppinglist.ShoppingListService;
import com.ezgroceries.shoppinglist.service.shoppinglist.model.ShoppingListResource;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ShoppingListController.class)
class ShoppingListControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ShoppingListService shoppingListService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void testCreateShoppingList() throws Exception {
        final UUID SHOPPING_LIST_ID = UUID.fromString("97c8e5bd-5353-426e-b57b-69eb2260ace3");
        final String SHOPPING_LIST_NAME = "Stephanie's birthday";

        // mock service layer
        final ShoppingListResource mockResource = new ShoppingListResource(SHOPPING_LIST_ID, SHOPPING_LIST_NAME);
        when(shoppingListService.createEmpty(SHOPPING_LIST_NAME)).thenReturn(mockResource);

        // create test input
        CreateNewShoppingListInputContract inputContract = new CreateNewShoppingListInputContract();
        inputContract.setName(SHOPPING_LIST_NAME);

        // perform test
        mockMvc.perform(post("/shopping-lists")
                .content(objectMapper.writeValueAsString(inputContract))
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("shoppingListId").value(SHOPPING_LIST_ID.toString()))
                .andExpect(jsonPath("name").value(SHOPPING_LIST_NAME));
    }

    @Test
    void testAddCocktailToShoppingList() throws Exception {
        final UUID SHOPPING_LIST_ID = UUID.fromString("97c8e5bd-5353-426e-b57b-69eb2260ace3");
        final String SHOPPING_LIST_NAME = "Stephanie's birthday";

        final CocktailResource cocktailResource = new CocktailResource(
                UUID.fromString("23b3d85a-3928-41c0-a533-6538a71e17c4"),
                "CocktailName",
                "CocktailGlass",
                "CocktailInstructions",
                "CocktailImageUrl",
                new HashSet<>(Arrays.asList("ingr1", "ingr2"))
        );

        // mock service layer
        final ShoppingListResource mockResource = new ShoppingListResource(SHOPPING_LIST_ID, SHOPPING_LIST_NAME);
        mockResource.setCocktails(new HashSet<>(Arrays.asList(cocktailResource)));
        when(shoppingListService.addCocktailsToShoppingList(SHOPPING_LIST_ID, Arrays.asList(UUID.fromString("23b3d85a-3928-41c0-a533-6538a71e17c4")))).thenReturn(mockResource);

        // perform test
        AddCocktailToShoppingListInputContract input[] = {
                new AddCocktailToShoppingListInputContract()
        };
        input[0].setCocktailId(UUID.fromString("23b3d85a-3928-41c0-a533-6538a71e17c4"));

        mockMvc.perform(post("/shopping-lists/{shoppingListId}/cocktails", SHOPPING_LIST_ID)
                .content(objectMapper.writeValueAsString(input))
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$..cocktailId").exists());
    }

    @Test
    void testGetShoppingList() throws Exception {
        final UUID SHOPPING_LIST_ID = UUID.fromString("97c8e5bd-5353-426e-b57b-69eb2260ace3");
        final String SHOPPING_LIST_NAME = "Stephanie's birthday";

        // mock service layer
        final ShoppingListResource mockResource = new ShoppingListResource(SHOPPING_LIST_ID, SHOPPING_LIST_NAME);
        when(shoppingListService.getShoppingList(SHOPPING_LIST_ID)).thenReturn(mockResource);

        mockMvc.perform(get("/shopping-lists/{shoppingListId}", SHOPPING_LIST_ID)
                .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("shoppingListId").value(SHOPPING_LIST_ID.toString()))
                .andExpect(jsonPath("name").value(SHOPPING_LIST_NAME))
                .andExpect(jsonPath("ingredients").exists());
    }

    @Test
    void testGetAllShoppingLists() throws Exception {
        final UUID SHOPPING_LIST_ID_1 = UUID.fromString("97c8e5bd-5353-426e-b57b-69eb2260ace3");
        final String SHOPPING_LIST_NAME_1 = "Stephanie's birthday";

        final UUID SHOPPING_LIST_ID_2 = UUID.fromString("97c8e5bd-5353-426e-b57b-69eb2260ace3");
        final String SHOPPING_LIST_NAME_2 = "Stephanie's birthday";

        final List<ShoppingListResource> mockResources = Arrays.asList(
                new ShoppingListResource(SHOPPING_LIST_ID_1, SHOPPING_LIST_NAME_1),
                new ShoppingListResource(SHOPPING_LIST_ID_2, SHOPPING_LIST_NAME_2)
        );

        // mock service layer
        when(shoppingListService.findAll()).thenReturn(mockResources);

        // perform test
        mockMvc.perform(get("/shopping-lists")
                .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$..shoppingListId").exists())
                .andExpect(jsonPath("$..name").exists())
                .andExpect(jsonPath("$..ingredients").exists());
    }

}
