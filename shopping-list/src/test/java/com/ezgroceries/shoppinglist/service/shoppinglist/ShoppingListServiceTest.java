package com.ezgroceries.shoppinglist.service.shoppinglist;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.ezgroceries.shoppinglist.repository.cocktail.entity.Cocktail;
import com.ezgroceries.shoppinglist.repository.shoppinglist.ShoppingListRepository;
import com.ezgroceries.shoppinglist.repository.shoppinglist.entity.ShoppingList;
import com.ezgroceries.shoppinglist.service.shoppinglist.model.ShoppingListResource;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ShoppingListServiceTest {

    @Mock
    private ShoppingListRepository shoppingListRepository;

    @InjectMocks
    private ShoppingListService shoppingListService;

    @Test
    void createEmpty() {
        ShoppingList mockShoppingList = new ShoppingList();
        mockShoppingList.setId(UUID.fromString("ff67c05b-5e98-4ea4-8db3-c4b106188063"));
        mockShoppingList.setName("Testname");

        // mock
        when(shoppingListRepository.save(any(ShoppingList.class))).thenReturn(mockShoppingList);

        // perform call
        ShoppingListResource actualShoppingListResource = shoppingListService.createEmpty("Testname");

        // verify
        assertEquals(UUID.fromString("ff67c05b-5e98-4ea4-8db3-c4b106188063"), actualShoppingListResource.getShoppingListId());
        assertEquals("Testname", actualShoppingListResource.getName());
    }

    @Test
    void addCocktailsToShoppingList() {
        ShoppingList mockShoppingList = new ShoppingList();
        mockShoppingList.setId(UUID.fromString("ff67c05b-5e98-4ea4-8db3-c4b106188063"));
        mockShoppingList.setName("Testname");

        ShoppingList mockShoppingListAfterSave = new ShoppingList();
        mockShoppingListAfterSave.setId(UUID.fromString("ff67c05b-5e98-4ea4-8db3-c4b106188063"));
        mockShoppingListAfterSave.setName("Testname");
        Cocktail cocktail1 = new Cocktail();
        cocktail1.setId(UUID.fromString("6e4f8d41-16fc-4c99-99a1-87e6079c228e"));
        Cocktail cocktail2 = new Cocktail();
        cocktail2.setId(UUID.fromString("ab8b8d12-d8e9-46a6-bde2-8da3ddb0bd18"));
        mockShoppingListAfterSave.setCocktails(new HashSet<>(Arrays.asList(cocktail1, cocktail2)));

        // mock
        when(shoppingListRepository.getOne(UUID.fromString("ff67c05b-5e98-4ea4-8db3-c4b106188063"))).thenReturn(mockShoppingList);
        when(shoppingListRepository.save(mockShoppingList)).thenReturn(mockShoppingList);

        // perform call
        ShoppingListResource actualShoppingListResource = shoppingListService.addCocktailsToShoppingList(
                UUID.fromString("ff67c05b-5e98-4ea4-8db3-c4b106188063"),
                Arrays.asList(
                        UUID.fromString("6e4f8d41-16fc-4c99-99a1-87e6079c228e"),
                        UUID.fromString("ab8b8d12-d8e9-46a6-bde2-8da3ddb0bd18")
                )
        );

        // verify
        assertEquals(UUID.fromString("ff67c05b-5e98-4ea4-8db3-c4b106188063"), actualShoppingListResource.getShoppingListId());
        assertEquals("Testname", actualShoppingListResource.getName());
    }

    @Test
    void getShoppingList() {
    }

    @Test
    void findAll() {
    }
}
