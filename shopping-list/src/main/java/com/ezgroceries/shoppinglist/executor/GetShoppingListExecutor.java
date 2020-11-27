package com.ezgroceries.shoppinglist.executor;

import com.ezgroceries.shoppinglist.web.contract.GetShoppingListOutputContract;
import com.ezgroceries.shoppinglist.service.model.ShoppingListResource;
import com.ezgroceries.shoppinglist.service.ShoppingListService;
import com.ezgroceries.shoppinglist.service.util.ShoppingListUtil;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class GetShoppingListExecutor {

    private final ShoppingListService shoppingListService;

    public GetShoppingListExecutor(ShoppingListService shoppingListService) {
        this.shoppingListService = shoppingListService;
    }

    public ResponseEntity<GetShoppingListOutputContract> invoke(UUID shoppingListId) {
        GetShoppingListOutputContract output = fillOutputContract(shoppingListService.getShoppingList(shoppingListId));

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
