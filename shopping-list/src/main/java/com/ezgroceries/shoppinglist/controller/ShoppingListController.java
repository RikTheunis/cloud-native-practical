package com.ezgroceries.shoppinglist.controller;

import com.ezgroceries.shoppinglist.contract.AddCocktailToShoppingListInputContract;
import com.ezgroceries.shoppinglist.contract.AddCocktailToShoppingListOutputContract;
import com.ezgroceries.shoppinglist.contract.CreateNewShoppingListInputContract;
import com.ezgroceries.shoppinglist.contract.CreateNewShoppingListOutputContract;
import com.ezgroceries.shoppinglist.contract.GetShoppingListOutputContract;
import com.ezgroceries.shoppinglist.executor.AddCocktailToShoppingListExecutor;
import com.ezgroceries.shoppinglist.executor.CreateNewShoppingListExecutor;
import com.ezgroceries.shoppinglist.executor.GetShoppingListExecutor;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/shopping-lists", produces = "application/json")
public class ShoppingListController {

    @PostMapping
    public ResponseEntity<CreateNewShoppingListOutputContract> createNewShoppingList(@RequestBody CreateNewShoppingListInputContract input) {

        return new CreateNewShoppingListExecutor().invoke(input);
    }

    @PostMapping(path = "{shoppingListId}/cocktails")
    public ResponseEntity<List<AddCocktailToShoppingListOutputContract>> addCocktailToShoppingList(@PathVariable UUID shoppingListId,
            @RequestBody List<AddCocktailToShoppingListInputContract> input) {

        return new AddCocktailToShoppingListExecutor().invoke(shoppingListId, input);
    }

    @GetMapping(path = "{shoppingListId}")
    public ResponseEntity<GetShoppingListOutputContract> getShoppingList(@PathVariable UUID shoppingListId) {

        return new GetShoppingListExecutor().invoke(shoppingListId);
    }

}
