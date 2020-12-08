package com.ezgroceries.shoppinglist.service.cocktail;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.ezgroceries.shoppinglist.external.client.CocktailDBClient;
import com.ezgroceries.shoppinglist.external.contract.CocktailDBSearchOutputContract;
import com.ezgroceries.shoppinglist.external.contract.DrinkResource;
import com.ezgroceries.shoppinglist.repository.cocktail.CocktailRepository;
import com.ezgroceries.shoppinglist.repository.cocktail.entity.CocktailEntity;
import com.ezgroceries.shoppinglist.repository.shoppinglist.entity.ShoppingListEntity;
import com.ezgroceries.shoppinglist.service.cocktail.model.CocktailResource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CocktailServiceTest {

    @Mock
    private CocktailRepository cocktailRepository;

    @Mock
    private CocktailDBClient cocktailDBClient;

    @InjectMocks
    private CocktailService cocktailService;

    @Test
    void searchCocktails() {
        ShoppingListEntity mockShoppingListEntity = new ShoppingListEntity();
        mockShoppingListEntity.setId(UUID.fromString("ff67c05b-5e98-4ea4-8db3-c4b106188063"));
        mockShoppingListEntity.setName("Testname");

        DrinkResource drinkResource = new DrinkResource();
        drinkResource.setIdDrink("TestId");
        drinkResource.setStrDrink("TestName");
        drinkResource.setStrDrinkThumb("TestImageThumbnail");
        drinkResource.setStrIngredient1("TestIngredient1");
        drinkResource.setStrIngredient2("TestIngredient2");
        drinkResource.setStrIngredient3("TestIngredient3");
        drinkResource.setStrGlass("TestGlass");
        drinkResource.setStrInstructions("TestInstructions");

        CocktailEntity mockCocktailEntity = new CocktailEntity();
        mockCocktailEntity.setId(UUID.fromString("ff67c05b-5e98-4ea4-8db3-c4b106188063"));
        mockCocktailEntity.setIdDrink("TestId");
        mockCocktailEntity.setName("TestName");
        Set<String> ingredients = new HashSet<>();
        ingredients.add("TestIngredient1");
        ingredients.add("TestIngredient2");
        ingredients.add("TestIngredient3");

        mockCocktailEntity.setIngredients(ingredients);


        CocktailDBSearchOutputContract cocktailDBMockSearchOutputContract = new CocktailDBSearchOutputContract();
        cocktailDBMockSearchOutputContract.setDrinks(Arrays.asList(drinkResource));

        // mock
        when(cocktailDBClient.searchCocktails("Test")).thenReturn(cocktailDBMockSearchOutputContract);
        when(cocktailRepository.findByIdDrinkIn(Arrays.asList("TestId"))).thenReturn(new ArrayList<>());
        when(cocktailRepository.save(any(CocktailEntity.class))).thenReturn(mockCocktailEntity);

        // perform call
        List<CocktailResource> cocktailResourceList = cocktailService.searchCocktails("Test");

        // verify
        assertEquals(1, cocktailResourceList.size());

        // verify
        CocktailResource cocktailResource = cocktailResourceList.get(0);
        assertEquals(UUID.fromString("ff67c05b-5e98-4ea4-8db3-c4b106188063"), cocktailResource.getCocktailId());
        assertEquals("TestName", cocktailResource.getName());
    }

}
