package com.ezgroceries.shoppinglist.executor;

import com.ezgroceries.shoppinglist.web.contract.CreateNewShoppingListInputContract;
import com.ezgroceries.shoppinglist.web.contract.CreateNewShoppingListOutputContract;
import com.ezgroceries.shoppinglist.service.model.ShoppingListResource;
import com.ezgroceries.shoppinglist.service.ShoppingListService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class CreateNewShoppingListExecutor {

    private final ShoppingListService shoppingListService;

    public CreateNewShoppingListExecutor(ShoppingListService shoppingListService) {
        this.shoppingListService = shoppingListService;
    }

    public ResponseEntity<CreateNewShoppingListOutputContract> invoke(CreateNewShoppingListInputContract input) {
        ShoppingListResource shoppingList = shoppingListService.createEmpty(input.getName());

        CreateNewShoppingListOutputContract output = new CreateNewShoppingListOutputContract();
        output.setName(shoppingList.getName());
        output.setShoppingListId(shoppingList.getShoppingListId());

        return new ResponseEntity<>(output, HttpStatus.CREATED);
    }

}
