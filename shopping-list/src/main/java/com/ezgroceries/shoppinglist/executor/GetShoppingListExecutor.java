package com.ezgroceries.shoppinglist.executor;

import com.ezgroceries.shoppinglist.DummyData;
import com.ezgroceries.shoppinglist.contract.GetShoppingListOutputContract;
import com.ezgroceries.shoppinglist.model.ShoppingListResource;
import com.ezgroceries.shoppinglist.util.ShoppingListUtil;
import java.util.UUID;
import org.springframework.http.ResponseEntity;

public class GetShoppingListExecutor {

    public ResponseEntity<GetShoppingListOutputContract> invoke(UUID shoppingListId) {
        GetShoppingListOutputContract output = fillOutputContract(DummyData.getDummyShoppingListResources().get(0));

        return ResponseEntity.ok(output);
    }

    private static GetShoppingListOutputContract fillOutputContract(ShoppingListResource shoppingList) {
        GetShoppingListOutputContract output = new GetShoppingListOutputContract();

        output.setShoppingListId(shoppingList.getShoppingListId());
        output.setName(shoppingList.getName());
        output.setIngredients(ShoppingListUtil.getAllIngredients(shoppingList));

        return output;
    }
}
