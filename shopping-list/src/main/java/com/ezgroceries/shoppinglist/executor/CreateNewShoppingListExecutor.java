package com.ezgroceries.shoppinglist.executor;

import com.ezgroceries.shoppinglist.DummyData;
import com.ezgroceries.shoppinglist.contract.CreateNewShoppingListInputContract;
import com.ezgroceries.shoppinglist.contract.CreateNewShoppingListOutputContract;
import com.ezgroceries.shoppinglist.model.ShoppingListResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CreateNewShoppingListExecutor {

    public ResponseEntity<CreateNewShoppingListOutputContract> invoke(CreateNewShoppingListInputContract input) {
        ShoppingListResource shoppingList = DummyData.getDummyShoppingListResources().get(0);

        CreateNewShoppingListOutputContract output = new CreateNewShoppingListOutputContract();
        output.setName(shoppingList.getName());
        output.setShoppingListId(shoppingList.getShoppingListId());

        return new ResponseEntity<>(output, HttpStatus.CREATED);
    }

}
