package com.ezgroceries.shoppinglist.executor;

import com.ezgroceries.shoppinglist.web.contract.AddCocktailToShoppingListInputContract;
import com.ezgroceries.shoppinglist.web.contract.AddCocktailToShoppingListOutputContract;
import com.ezgroceries.shoppinglist.service.model.ShoppingListResource;
import com.ezgroceries.shoppinglist.service.ShoppingListService;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class AddCocktailsToShoppingListExecutor {

    private final ShoppingListService shoppingListService;

    public AddCocktailsToShoppingListExecutor(ShoppingListService shoppingListService) {
        this.shoppingListService = shoppingListService;
    }

    public ResponseEntity<List<AddCocktailToShoppingListOutputContract>> invoke(
            UUID shoppingListId,
            List<AddCocktailToShoppingListInputContract> input) {
        List<UUID> cocktailIds = input.stream().map(AddCocktailToShoppingListInputContract::getCocktailId).collect(Collectors.toList());

        ShoppingListResource shoppingList = shoppingListService.addCocktailsToShoppingList(shoppingListId, cocktailIds);

        List<AddCocktailToShoppingListOutputContract> outputList = shoppingList.getCocktails()
                .stream()
                .map(cocktail -> {
                    AddCocktailToShoppingListOutputContract output = new AddCocktailToShoppingListOutputContract();
                    output.setCocktailId(cocktail.getCocktailId());
                    return output;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(outputList);
    }
}
