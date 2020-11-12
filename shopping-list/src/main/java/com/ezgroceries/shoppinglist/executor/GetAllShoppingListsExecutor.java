package com.ezgroceries.shoppinglist.executor;

import com.ezgroceries.shoppinglist.DummyData;
import com.ezgroceries.shoppinglist.contract.GetAllShoppingListsOutputContract;
import com.ezgroceries.shoppinglist.model.ShoppingListResource;
import com.ezgroceries.shoppinglist.util.ShoppingListUtil;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;

public class GetAllShoppingListsExecutor {

    public ResponseEntity<List<GetAllShoppingListsOutputContract>> invoke() {
        List<GetAllShoppingListsOutputContract> outputList = DummyData.getDummyShoppingListResources()
                .stream()
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
