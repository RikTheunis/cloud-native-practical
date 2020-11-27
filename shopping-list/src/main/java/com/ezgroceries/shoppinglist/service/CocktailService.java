package com.ezgroceries.shoppinglist.service;

import com.ezgroceries.shoppinglist.external.contract.DrinkResource;
import com.ezgroceries.shoppinglist.service.model.CocktailResource;
import com.ezgroceries.shoppinglist.data.entity.Cocktail;
import com.ezgroceries.shoppinglist.data.CocktailRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class CocktailService {

    private final CocktailRepository cocktailRepository;

    public CocktailService(CocktailRepository cocktailRepository) {
        this.cocktailRepository = cocktailRepository;
    }

    public List<CocktailResource> mergeCocktails(List<DrinkResource> drinks) {
        //Get all the idDrink attributes
        List<String> ids = drinks.stream().map(DrinkResource::getIdDrink).collect(Collectors.toList());

        //Get all the ones we already have from our DB, use a Map for convenient lookup
        Map<String, Cocktail> existingEntityMap = cocktailRepository.findByIdDrinkIn(ids).stream().collect(Collectors.toMap(Cocktail::getIdDrink, o -> o, (o, o2) -> o));

        //Stream over all the drinks, map them to the existing ones, persist a new one if not existing
        Map<String, Cocktail> allEntityMap = drinks.stream().map(drinkResource -> {
            Cocktail cocktail = existingEntityMap.get(drinkResource.getIdDrink());
            if (cocktail == null) {
                Cocktail newCocktail = new Cocktail();
                newCocktail.setId(UUID.randomUUID());
                newCocktail.setIdDrink(drinkResource.getIdDrink());
                newCocktail.setName(drinkResource.getStrDrink());
                newCocktail.setIngredients(extractIngredientsFromDrink(drinkResource));
                cocktail = cocktailRepository.save(newCocktail);
            }
            return cocktail;
        }).collect(Collectors.toMap(Cocktail::getIdDrink, o -> o, (o, o2) -> o));

        //Merge drinks and our entities, transform to CocktailResource instances
        return mergeAndTransform(drinks, allEntityMap);
    }

    private List<CocktailResource> mergeAndTransform(List<DrinkResource> drinks, Map<String, Cocktail> allEntityMap) {
        return drinks.stream().map(drinkResource -> new CocktailResource(allEntityMap.get(drinkResource.getIdDrink()).getId(), drinkResource.getStrDrink(), drinkResource.getStrGlass(),
                drinkResource.getStrInstructions(), drinkResource.getStrDrinkThumb(), extractIngredientsFromDrink(drinkResource))).collect(Collectors.toList());
    }

    private static Set<String> extractIngredientsFromDrink(DrinkResource drink) {
        Set<String> ingredients = new HashSet<>();

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
