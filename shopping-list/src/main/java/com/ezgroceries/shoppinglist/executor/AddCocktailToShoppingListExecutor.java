package com.ezgroceries.shoppinglist.executor;

import com.ezgroceries.shoppinglist.DummyData;
import com.ezgroceries.shoppinglist.contract.AddCocktailToShoppingListInputContract;
import com.ezgroceries.shoppinglist.contract.AddCocktailToShoppingListOutputContract;
import com.ezgroceries.shoppinglist.model.ShoppingListResource;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;

public class AddCocktailToShoppingListExecutor {

    public ResponseEntity<List<AddCocktailToShoppingListOutputContract>> invoke(UUID shoppingListId,
            List<AddCocktailToShoppingListInputContract> input) {
        ShoppingListResource shoppingList = DummyData.getDummyShoppingListResources().get(0);

        List<AddCocktailToShoppingListOutputContract> outputList = shoppingList.getCocktails()
                .subList(0, 1)
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
