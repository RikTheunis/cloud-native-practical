package com.ezgroceries.shoppinglist.service.cocktail;

import com.ezgroceries.shoppinglist.external.client.CocktailDBClient;
import com.ezgroceries.shoppinglist.external.contract.CocktailDBSearchResponse;
import com.ezgroceries.shoppinglist.external.contract.DrinkResource;
import com.ezgroceries.shoppinglist.repository.cocktail.CocktailRepository;
import com.ezgroceries.shoppinglist.repository.cocktail.entity.CocktailEntity;
import com.ezgroceries.shoppinglist.service.cocktail.model.Cocktail;
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

    private final CocktailDBClient cocktailDBClient;
    private final CocktailRepository cocktailRepository;

    public CocktailService(CocktailRepository cocktailRepository, CocktailDBClient cocktailDBClient) {
        this.cocktailRepository = cocktailRepository;
        this.cocktailDBClient = cocktailDBClient;
    }

    public List<Cocktail> searchCocktails(String search) {
        CocktailDBSearchResponse cocktailDBSearchOutput = cocktailDBClient.searchCocktails(search);
        return this.mergeCocktails(cocktailDBSearchOutput.getDrinks());
    }

    private List<Cocktail> mergeCocktails(List<DrinkResource> drinks) {
        //Get all the idDrink attributes
        List<String> ids = drinks.stream().map(DrinkResource::getIdDrink).collect(Collectors.toList());

        //Get all the ones we already have from our DB, use a Map for convenient lookup
        Map<String, CocktailEntity> existingEntityMap = cocktailRepository.findByIdDrinkIn(ids).stream().collect(Collectors.toMap(CocktailEntity::getIdDrink, o -> o, (o, o2) -> o));

        //Stream over all the drinks, map them to the existing ones, persist a new one if not existing
        Map<String, CocktailEntity> allEntityMap = drinks.stream().map(drinkResource -> {
            CocktailEntity cocktailEntity = existingEntityMap.get(drinkResource.getIdDrink());
            if (cocktailEntity == null) {
                CocktailEntity newCocktailEntity = new CocktailEntity();
                newCocktailEntity.setId(UUID.randomUUID());
                newCocktailEntity.setIdDrink(drinkResource.getIdDrink());
                newCocktailEntity.setName(drinkResource.getStrDrink());
                newCocktailEntity.setGlass(drinkResource.getStrGlass());
                newCocktailEntity.setInstructions(drinkResource.getStrInstructions());
                newCocktailEntity.setImageUrl(drinkResource.getStrDrinkThumb());
                newCocktailEntity.setIngredients(extractIngredientsFromDrink(drinkResource));
                cocktailEntity = cocktailRepository.save(newCocktailEntity);
            }
            return cocktailEntity;
        }).collect(Collectors.toMap(CocktailEntity::getIdDrink, o -> o, (o, o2) -> o));

        //Merge drinks and our entities, transform to Cocktail instances
        return mergeAndTransform(drinks, allEntityMap);
    }

    private List<Cocktail> mergeAndTransform(List<DrinkResource> drinks, Map<String, CocktailEntity> allEntityMap) {
        return drinks
                .stream()
                .map(drinkResource -> new Cocktail(
                        allEntityMap.get(drinkResource.getIdDrink()).getId(),
                        drinkResource.getStrDrink(),
                        drinkResource.getStrGlass(),
                        drinkResource.getStrInstructions(),
                        drinkResource.getStrDrinkThumb(),
                        extractIngredientsFromDrink(drinkResource)
                )).collect(Collectors.toList());
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
