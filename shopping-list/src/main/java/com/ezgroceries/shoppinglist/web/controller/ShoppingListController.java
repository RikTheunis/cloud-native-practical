package com.ezgroceries.shoppinglist.web.controller;

import com.ezgroceries.shoppinglist.service.ShoppingListService;
import com.ezgroceries.shoppinglist.service.model.ShoppingListResource;
import com.ezgroceries.shoppinglist.service.util.ShoppingListUtil;
import com.ezgroceries.shoppinglist.web.contract.AddCocktailToShoppingListInputContract;
import com.ezgroceries.shoppinglist.web.contract.AddCocktailToShoppingListOutputContract;
import com.ezgroceries.shoppinglist.web.contract.CreateNewShoppingListInputContract;
import com.ezgroceries.shoppinglist.web.contract.CreateNewShoppingListOutputContract;
import com.ezgroceries.shoppinglist.web.contract.GetAllShoppingListsOutputContract;
import com.ezgroceries.shoppinglist.web.contract.GetShoppingListOutputContract;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
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

    private final ShoppingListService shoppingListService;

    public ShoppingListController(ShoppingListService shoppingListService) {
        this.shoppingListService = shoppingListService;
    }

    @PostMapping
    public ResponseEntity<CreateNewShoppingListOutputContract> createNewShoppingList(@RequestBody CreateNewShoppingListInputContract input) {
        ShoppingListResource shoppingList = shoppingListService.createEmpty(input.getName());

        CreateNewShoppingListOutputContract output = new CreateNewShoppingListOutputContract();
        output.setName(shoppingList.getName());
        output.setShoppingListId(shoppingList.getShoppingListId());

        return new ResponseEntity<>(output, HttpStatus.CREATED);
    }

    @PostMapping(path = "{shoppingListId}/cocktails")
    public ResponseEntity<List<AddCocktailToShoppingListOutputContract>> addCocktailToShoppingList(
            @PathVariable UUID shoppingListId,
            @RequestBody List<AddCocktailToShoppingListInputContract> input
    ) {
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

    @GetMapping(path = "{shoppingListId}")
    public ResponseEntity<GetShoppingListOutputContract> getShoppingList(@PathVariable UUID shoppingListId) {
        ShoppingListResource shoppingList = shoppingListService.getShoppingList(shoppingListId);

        GetShoppingListOutputContract output = new GetShoppingListOutputContract();

        output.setShoppingListId(shoppingList.getShoppingListId());
        output.setName(shoppingList.getName());
        output.setIngredients(ShoppingListUtil.getAllIngredients(shoppingList));

        return ResponseEntity.ok(output);
    }

    @GetMapping
    public ResponseEntity<List<GetAllShoppingListsOutputContract>> getAllShoppingLists() {
        List<GetAllShoppingListsOutputContract> outputList = shoppingListService.findAll().stream()
                .map(shoppingList -> {
                    GetAllShoppingListsOutputContract output = new GetAllShoppingListsOutputContract();
                    output.setShoppingListId(shoppingList.getShoppingListId());
                    output.setName(shoppingList.getName());
                    output.setIngredients(ShoppingListUtil.getAllIngredients(shoppingList));
                    return output;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(outputList);
    }

}
