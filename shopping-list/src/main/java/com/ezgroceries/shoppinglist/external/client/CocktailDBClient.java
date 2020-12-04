package com.ezgroceries.shoppinglist.external.client;

import com.ezgroceries.shoppinglist.external.contract.CocktailDBSearchOutputContract;
import com.ezgroceries.shoppinglist.external.contract.DrinkResource;
import com.ezgroceries.shoppinglist.repository.cocktail.CocktailRepository;
import com.ezgroceries.shoppinglist.repository.cocktail.entity.Cocktail;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(name = "cocktailDBClient", url = "https://www.thecocktaildb.com/api/json/v1/1", fallback = CocktailDBClient.CocktailDBClientFallback.class)
public interface CocktailDBClient {

    @GetMapping(value = "search.php", produces = "application/json")
    CocktailDBSearchOutputContract searchCocktails(@RequestParam("s") String search);

    @Component
    class CocktailDBClientFallback implements CocktailDBClient {

        private final CocktailRepository cocktailRepository;

        public CocktailDBClientFallback(CocktailRepository cocktailRepository) {
            this.cocktailRepository = cocktailRepository;
        }

        @Override
        public CocktailDBSearchOutputContract searchCocktails(String search) {
            List<Cocktail> cocktailEntities = cocktailRepository.findByNameContainingIgnoreCase(search);

            CocktailDBSearchOutputContract cocktailDBResponse = new CocktailDBSearchOutputContract();
            cocktailDBResponse.setDrinks(
                    cocktailEntities
                            .stream()
                            .map(CocktailDBClientFallback::mapCocktailToDrinkResource)
                            .collect(Collectors.toList())
            );

            return cocktailDBResponse;
        }

        private static DrinkResource mapCocktailToDrinkResource(Cocktail cocktail) {
            DrinkResource drinkResource = new DrinkResource();
            drinkResource.setIdDrink(cocktail.getIdDrink());
            drinkResource.setStrDrink(cocktail.getName());
            drinkResource.setStrGlass(cocktail.getGlass());
            drinkResource.setStrInstructions(cocktail.getInstructions());
            drinkResource.setStrDrinkThumb(cocktail.getImageUrl());

            // TODO refactor
            List<String> ingredients = new ArrayList<>(cocktail.getIngredients());
            drinkResource.setStrIngredient1(getOrNull(ingredients, 0));
            drinkResource.setStrIngredient2(getOrNull(ingredients, 1));
            drinkResource.setStrIngredient3(getOrNull(ingredients, 2));
            drinkResource.setStrIngredient4(getOrNull(ingredients, 3));
            drinkResource.setStrIngredient5(getOrNull(ingredients, 4));
            drinkResource.setStrIngredient6(getOrNull(ingredients, 5));
            drinkResource.setStrIngredient7(getOrNull(ingredients, 6));
            drinkResource.setStrIngredient8(getOrNull(ingredients, 7));
            drinkResource.setStrIngredient9(getOrNull(ingredients, 8));
            drinkResource.setStrIngredient10(getOrNull(ingredients, 9));
            drinkResource.setStrIngredient11(getOrNull(ingredients, 10));
            drinkResource.setStrIngredient12(getOrNull(ingredients, 11));
            drinkResource.setStrIngredient13(getOrNull(ingredients, 12));
            drinkResource.setStrIngredient14(getOrNull(ingredients, 13));
            drinkResource.setStrIngredient15(getOrNull(ingredients, 14));

            return drinkResource;
        }

        private static String getOrNull(List<String> list, int index) {
            if (index < list.size()) {
                return list.get(index);
            }
            return null;
        }
    }

}
