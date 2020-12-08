package com.ezgroceries.shoppinglist.service.shoppinglist;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.ezgroceries.shoppinglist.repository.cocktail.CocktailRepository;
import com.ezgroceries.shoppinglist.repository.cocktail.entity.CocktailEntity;
import com.ezgroceries.shoppinglist.repository.shoppinglist.ShoppingListRepository;
import com.ezgroceries.shoppinglist.repository.shoppinglist.entity.ShoppingListEntity;
import com.ezgroceries.shoppinglist.service.shoppinglist.model.ShoppingList;
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
        ShoppingListEntity mockShoppingListEntity = new ShoppingListEntity();
        mockShoppingListEntity.setId(UUID.fromString("ff67c05b-5e98-4ea4-8db3-c4b106188063"));
        mockShoppingListEntity.setName("Testname");

        // mock
        when(shoppingListRepository.save(any(ShoppingListEntity.class))).thenReturn(mockShoppingListEntity);

        // perform call
        ShoppingList actualShoppingList = shoppingListService.createEmptyShoppingList("Testname", "testUser");

        // verify
        assertEquals(UUID.fromString("ff67c05b-5e98-4ea4-8db3-c4b106188063"), actualShoppingList.getShoppingListId());
        assertEquals("Testname", actualShoppingList.getName());
    }

    @Test
    void addCocktailsToShoppingList() {
        ShoppingListEntity mockShoppingListEntity = new ShoppingListEntity();
        mockShoppingListEntity.setId(UUID.fromString("ff67c05b-5e98-4ea4-8db3-c4b106188063"));
        mockShoppingListEntity.setName("Testname");
        mockShoppingListEntity.setCocktails(new HashSet<>());

        ShoppingListEntity mockShoppingListEntityAfterSave = new ShoppingListEntity();
        mockShoppingListEntityAfterSave.setId(UUID.fromString("ff67c05b-5e98-4ea4-8db3-c4b106188063"));
        mockShoppingListEntityAfterSave.setName("Testname");
        CocktailEntity cocktailEntity1 = new CocktailEntity();
        cocktailEntity1.setId(UUID.fromString("6e4f8d41-16fc-4c99-99a1-87e6079c228e"));
        mockShoppingListEntityAfterSave.setCocktails(new HashSet<>(Arrays.asList(cocktailEntity1)));

        // mock
        when(shoppingListRepository.getOne(UUID.fromString("ff67c05b-5e98-4ea4-8db3-c4b106188063"))).thenReturn(mockShoppingListEntity);
        when(cocktailRepository.getOne(any())).thenReturn(cocktailEntity1);
        when(shoppingListRepository.save(any(ShoppingListEntity.class))).thenReturn(mockShoppingListEntityAfterSave);

        // perform call
        ShoppingList actualShoppingList = shoppingListService.addCocktailsToShoppingList(
                UUID.fromString("ff67c05b-5e98-4ea4-8db3-c4b106188063"),
                Arrays.asList(
                        UUID.fromString("6e4f8d41-16fc-4c99-99a1-87e6079c228e")
                )
        );

        // verify
        assertEquals(UUID.fromString("ff67c05b-5e98-4ea4-8db3-c4b106188063"), actualShoppingList.getShoppingListId());
        assertEquals("Testname", actualShoppingList.getName());
        assertEquals(1, actualShoppingList.getCocktails().size());
    }

    @Test
    void getShoppingList() {
    }

    @Test
    void findAll() {
    }
}
