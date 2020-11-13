package com.ezgroceries.shoppinglist.executor;

import com.ezgroceries.shoppinglist.backend.client.CocktailDBClient;
import com.ezgroceries.shoppinglist.backend.contract.CocktailDBSearchOutputContract;
import com.ezgroceries.shoppinglist.backend.contract.DrinkResource;
import com.ezgroceries.shoppinglist.contract.SearchCocktailOutputContract;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class SearchCocktailExecutor {

    private final CocktailDBClient cocktailDBClient;

    public SearchCocktailExecutor(CocktailDBClient cocktailDBClient) {
        this.cocktailDBClient = cocktailDBClient;
    }

    public ResponseEntity<List<SearchCocktailOutputContract>> invoke(String search) {
        CocktailDBSearchOutputContract cocktailDBSearchOutput = cocktailDBClient.searchCocktails(search);

        List<SearchCocktailOutputContract> outputList = cocktailDBSearchOutput.getDrinks()
                .stream()
                .map(drink -> drinkToOutputContract(drink))
                .collect(Collectors.toList());

        return ResponseEntity.ok(outputList);
    }

    private static SearchCocktailOutputContract drinkToOutputContract(DrinkResource drink) {
        SearchCocktailOutputContract output = new SearchCocktailOutputContract();

        output.setCocktailId(UUID.randomUUID());
        output.setName(drink.getStrDrink());
        output.setGlass(drink.getStrGlass());
        output.setImage(drink.getStrDrinkThumb());
        output.setInstructions(drink.getStrInstructions());

        output.setIngredients(extractIngredientsFromDrink(drink));

        return output;
    }

    private static List<String> extractIngredientsFromDrink(DrinkResource drink) {
        List<String> ingredients = new ArrayList<>();

        ingredients.add(drink.getStrIngredient1());
        ingredients.add(drink.getStrIngredient2());
        ingredients.add(drink.getStrIngredient3());
        ingredients.add(drink.getStrIngredient4());
        ingredients.add(drink.getStrIngredient5());
        ingredients.add(drink.getStrIngredient6());
        ingredients.add(drink.getStrIngredient7());
        ingredients.add(drink.getStrIngredient8());
        ingredients.add(drink.getStrIngredient9());
        ingredients.add(drink.getStrIngredient10());
        ingredients.add(drink.getStrIngredient11());
        ingredients.add(drink.getStrIngredient12());
        ingredients.add(drink.getStrIngredient13());
        ingredients.add(drink.getStrIngredient14());
        ingredients.add(drink.getStrIngredient15());

        ingredients.removeIf(Objects::isNull);

        return ingredients;
    }

}
