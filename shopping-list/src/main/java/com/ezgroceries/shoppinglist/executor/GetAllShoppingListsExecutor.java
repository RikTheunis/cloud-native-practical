package com.ezgroceries.shoppinglist.executor;

import com.ezgroceries.shoppinglist.contract.GetAllShoppingListsOutputContract;
import com.ezgroceries.shoppinglist.model.ShoppingListResource;
import com.ezgroceries.shoppinglist.service.ShoppingListService;
import com.ezgroceries.shoppinglist.util.ShoppingListUtil;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class GetAllShoppingListsExecutor {

    private final ShoppingListService shoppingListService;

    public GetAllShoppingListsExecutor(ShoppingListService shoppingListService) {
        this.shoppingListService = shoppingListService;
    }

    public ResponseEntity<List<GetAllShoppingListsOutputContract>> invoke() {
        List<GetAllShoppingListsOutputContract> outputList = shoppingListService.findAll().stream()
                .map(GetAllShoppingListsExecutor::fillOutputContract)
                .collect(Collectors.toList());

        return ResponseEntity.ok(outputList);
    }

    private static GetAllShoppingListsOutputContract fillOutputContract(ShoppingListResource shoppingList) {
        GetAllShoppingListsOutputContract output = new GetAllShoppingListsOutputContract();

        output.setShoppingListId(shoppingList.getShoppingListId());
        output.setName(shoppingList.getName());
        output.setIngredients(ShoppingListUtil.getAllIngredients(shoppingList));

        return output;
    }
}
