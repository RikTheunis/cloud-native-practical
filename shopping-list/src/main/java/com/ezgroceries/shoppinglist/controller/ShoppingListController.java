package com.ezgroceries.shoppinglist.controller;

import com.ezgroceries.shoppinglist.contract.CreateNewShoppingListInputContract;
import com.ezgroceries.shoppinglist.contract.CreateNewShoppingListOutputContract;
import com.ezgroceries.shoppinglist.executor.CreateNewShoppingListExecutor;
import org.springframework.http.ResponseEntity;
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

}
