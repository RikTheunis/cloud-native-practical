package com.ezgroceries.shoppinglist.service.shoppinglist;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.ezgroceries.shoppinglist.repository.cocktail.CocktailRepository;
import com.ezgroceries.shoppinglist.repository.cocktail.entity.Cocktail;
import com.ezgroceries.shoppinglist.repository.shoppinglist.ShoppingListRepository;
import com.ezgroceries.shoppinglist.repository.shoppinglist.entity.ShoppingList;
import com.ezgroceries.shoppinglist.service.shoppinglist.model.ShoppingListResource;
import java.util.Arrays;
import java.util.HashSet;
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

    @Mock
    private CocktailRepository cocktailRepository;

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
        ShoppingListResource actualShoppingListResource = shoppingListService.createEmptyShoppingList("Testname", "testUser");

        // verify
        assertEquals(UUID.fromString("ff67c05b-5e98-4ea4-8db3-c4b106188063"), actualShoppingListResource.getShoppingListId());
        assertEquals("Testname", actualShoppingListResource.getName());
    }

    @Test
    void addCocktailsToShoppingList() {
        ShoppingList mockShoppingList = new ShoppingList();
        mockShoppingList.setId(UUID.fromString("ff67c05b-5e98-4ea4-8db3-c4b106188063"));
        mockShoppingList.setName("Testname");
        mockShoppingList.setCocktails(new HashSet<>());

        ShoppingList mockShoppingListAfterSave = new ShoppingList();
        mockShoppingListAfterSave.setId(UUID.fromString("ff67c05b-5e98-4ea4-8db3-c4b106188063"));
        mockShoppingListAfterSave.setName("Testname");
        Cocktail cocktail1 = new Cocktail();
        cocktail1.setId(UUID.fromString("6e4f8d41-16fc-4c99-99a1-87e6079c228e"));
        mockShoppingListAfterSave.setCocktails(new HashSet<>(Arrays.asList(cocktail1)));

        // mock
        when(shoppingListRepository.getOne(UUID.fromString("ff67c05b-5e98-4ea4-8db3-c4b106188063"))).thenReturn(mockShoppingList);
        when(cocktailRepository.getOne(any())).thenReturn(cocktail1);
        when(shoppingListRepository.save(any(ShoppingList.class))).thenReturn(mockShoppingListAfterSave);

        // perform call
        ShoppingListResource actualShoppingListResource = shoppingListService.addCocktailsToShoppingList(
                UUID.fromString("ff67c05b-5e98-4ea4-8db3-c4b106188063"),
                Arrays.asList(
                        UUID.fromString("6e4f8d41-16fc-4c99-99a1-87e6079c228e")
                )
        );

        // verify
        assertEquals(UUID.fromString("ff67c05b-5e98-4ea4-8db3-c4b106188063"), actualShoppingListResource.getShoppingListId());
        assertEquals("Testname", actualShoppingListResource.getName());
        assertEquals(1, actualShoppingListResource.getCocktails().size());
    }

    @Test
    void getShoppingList() {
    }

    @Test
    void findAll() {
    }
}
