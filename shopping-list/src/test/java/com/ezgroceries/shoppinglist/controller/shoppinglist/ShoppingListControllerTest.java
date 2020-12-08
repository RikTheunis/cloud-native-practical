package com.ezgroceries.shoppinglist.controller.shoppinglist;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ezgroceries.shoppinglist.controller.shoppinglist.contract.AddCocktailToShoppingListRequest;
import com.ezgroceries.shoppinglist.controller.shoppinglist.contract.CreateNewShoppingListRequest;
import com.ezgroceries.shoppinglist.service.cocktail.model.Cocktail;
import com.ezgroceries.shoppinglist.service.shoppinglist.ShoppingListService;
import com.ezgroceries.shoppinglist.service.shoppinglist.model.ShoppingList;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ShoppingListController.class)
@ActiveProfiles("test")
class ShoppingListControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ShoppingListService shoppingListService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @WithMockUser(username="testOwner")
    void testCreateShoppingList() throws Exception {
        final UUID SHOPPING_LIST_ID = UUID.fromString("97c8e5bd-5353-426e-b57b-69eb2260ace3");
        final String SHOPPING_LIST_NAME = "Stephanie's birthday";
        final String SHOPPING_LIST_OWNER_NAME = "testOwner";

        // mock service layer
        final ShoppingList mockResource = new ShoppingList(SHOPPING_LIST_ID, SHOPPING_LIST_NAME, SHOPPING_LIST_OWNER_NAME);
        when(shoppingListService.createEmptyShoppingList(SHOPPING_LIST_NAME, "testOwner")).thenReturn(mockResource);

        // create test input
        CreateNewShoppingListRequest inputContract = new CreateNewShoppingListRequest();
        inputContract.setName(SHOPPING_LIST_NAME);

        // perform test
        mockMvc.perform(post("/shopping-lists")
                .content(objectMapper.writeValueAsString(inputContract))
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf())
        )
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("shoppingListId").value(SHOPPING_LIST_ID.toString()))
                .andExpect(jsonPath("name").value(SHOPPING_LIST_NAME));
    }

    @Test
    @WithMockUser(username="testOwner")
    void testAddCocktailToShoppingList() throws Exception {
        final UUID SHOPPING_LIST_ID = UUID.fromString("97c8e5bd-5353-426e-b57b-69eb2260ace3");
        final String SHOPPING_LIST_NAME = "Stephanie's birthday";
        final String SHOPPING_LIST_OWNER_NAME = "testOwner";

        final Cocktail cocktail = new Cocktail(
                UUID.fromString("23b3d85a-3928-41c0-a533-6538a71e17c4"),
                "CocktailName",
                "CocktailGlass",
                "CocktailInstructions",
                "CocktailImageUrl",
                new HashSet<>(Arrays.asList("ingr1", "ingr2"))
        );

        // mock service layer
        final ShoppingList mockResource = new ShoppingList(SHOPPING_LIST_ID, SHOPPING_LIST_NAME, SHOPPING_LIST_OWNER_NAME);
        mockResource.setCocktails(new HashSet<>(Arrays.asList(cocktail)));
        when(shoppingListService.addCocktailsToShoppingList(SHOPPING_LIST_ID, Arrays.asList(UUID.fromString("23b3d85a-3928-41c0-a533-6538a71e17c4")))).thenReturn(mockResource);

        // perform test
        AddCocktailToShoppingListRequest input[] = {
                new AddCocktailToShoppingListRequest()
        };
        input[0].setCocktailId(UUID.fromString("23b3d85a-3928-41c0-a533-6538a71e17c4"));

        mockMvc.perform(post("/shopping-lists/{shoppingListId}/cocktails", SHOPPING_LIST_ID)
                .content(objectMapper.writeValueAsString(input))
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf())
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$..cocktailId").exists());
    }

    @Test
    @WithMockUser(username="testOwner")
    void testGetShoppingList() throws Exception {
        final UUID SHOPPING_LIST_ID = UUID.fromString("97c8e5bd-5353-426e-b57b-69eb2260ace3");
        final String SHOPPING_LIST_NAME = "Stephanie's birthday";
        final String SHOPPING_LIST_OWNER_NAME = "testOwner";

        // mock service layer
        final ShoppingList mockResource = new ShoppingList(SHOPPING_LIST_ID, SHOPPING_LIST_NAME, SHOPPING_LIST_OWNER_NAME);
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
    @WithMockUser(username="testOwner")
    void testGetAllShoppingLists() throws Exception {
        final UUID SHOPPING_LIST_ID_1 = UUID.fromString("97c8e5bd-5353-426e-b57b-69eb2260ace3");
        final String SHOPPING_LIST_NAME_1 = "Stephanie's birthday";
        final String SHOPPING_LIST_OWNER_NAME_1 = "testOwner1";

        final UUID SHOPPING_LIST_ID_2 = UUID.fromString("97c8e5bd-5353-426e-b57b-69eb2260ace3");
        final String SHOPPING_LIST_NAME_2 = "Stephanie's birthday";
        final String SHOPPING_LIST_OWNER_NAME_2 = "testOwner2";

        final List<ShoppingList> mockResources = Arrays.asList(
                new ShoppingList(SHOPPING_LIST_ID_1, SHOPPING_LIST_NAME_1, SHOPPING_LIST_OWNER_NAME_1),
                new ShoppingList(SHOPPING_LIST_ID_2, SHOPPING_LIST_NAME_2, SHOPPING_LIST_OWNER_NAME_2)
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

    @Test
    void testGetAllShoppingListsNotAuthenticated() throws Exception {

        // perform test
        mockMvc.perform(get("/shopping-lists")
                .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isUnauthorized());
    }

}
