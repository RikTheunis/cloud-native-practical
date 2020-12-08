package com.ezgroceries.shoppinglist.service.shoppinglist.util;

import com.ezgroceries.shoppinglist.service.shoppinglist.model.ShoppingList;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ShoppingListUtil {

    public static List<String> getAllIngredients(ShoppingList shoppingList) {
        Set<String> ingredients = new HashSet<>();

        shoppingList.getCocktails().forEach(cocktail -> ingredients.addAll(cocktail.getIngredients()));

        return new ArrayList<>(ingredients);
    }
}
