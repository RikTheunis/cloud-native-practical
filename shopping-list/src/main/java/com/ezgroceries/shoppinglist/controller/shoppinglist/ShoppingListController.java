package com.ezgroceries.shoppinglist.controller.shoppinglist;

import com.ezgroceries.shoppinglist.controller.shoppinglist.contract.AddCocktailToShoppingListRequest;
import com.ezgroceries.shoppinglist.controller.shoppinglist.contract.AddCocktailToShoppingListResponse;
import com.ezgroceries.shoppinglist.controller.shoppinglist.contract.CreateNewShoppingListRequest;
import com.ezgroceries.shoppinglist.controller.shoppinglist.contract.CreateNewShoppingListResponse;
import com.ezgroceries.shoppinglist.controller.shoppinglist.contract.GetAllShoppingListsResponse;
import com.ezgroceries.shoppinglist.controller.shoppinglist.contract.GetShoppingListResponse;
import com.ezgroceries.shoppinglist.service.shoppinglist.ShoppingListService;
import com.ezgroceries.shoppinglist.service.shoppinglist.model.ShoppingList;
import com.ezgroceries.shoppinglist.service.shoppinglist.util.ShoppingListUtil;
import java.security.Principal;
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
    public ResponseEntity<CreateNewShoppingListResponse> createNewShoppingList(
            @RequestBody CreateNewShoppingListRequest input,
            Principal principal
    ) {
        ShoppingList shoppingList = shoppingListService.createEmptyShoppingList(input.getName(), principal.getName());

        CreateNewShoppingListResponse output = new CreateNewShoppingListResponse();
        output.setName(shoppingList.getName());
        output.setShoppingListId(shoppingList.getShoppingListId());

        return new ResponseEntity<>(output, HttpStatus.CREATED);
    }

    @PostMapping(path = "{shoppingListId}/cocktails")
    public ResponseEntity<List<AddCocktailToShoppingListResponse>> addCocktailToShoppingList(
            @PathVariable UUID shoppingListId,
            @RequestBody List<AddCocktailToShoppingListRequest> input
    ) {
        List<UUID> cocktailIds = input.stream().map(AddCocktailToShoppingListRequest::getCocktailId).collect(Collectors.toList());

        ShoppingList shoppingList = shoppingListService.addCocktailsToShoppingList(shoppingListId, cocktailIds);

        List<AddCocktailToShoppingListResponse> outputList = shoppingList.getCocktails()
                .stream()
                .map(cocktail -> {
                    AddCocktailToShoppingListResponse output = new AddCocktailToShoppingListResponse();
                    output.setCocktailId(cocktail.getCocktailId());
                    return output;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(outputList);
    }

    @GetMapping(path = "{shoppingListId}")
    public ResponseEntity<GetShoppingListResponse> getShoppingList(@PathVariable UUID shoppingListId) {
        ShoppingList shoppingList = shoppingListService.getShoppingList(shoppingListId);

        GetShoppingListResponse output = new GetShoppingListResponse();

        output.setShoppingListId(shoppingList.getShoppingListId());
        output.setName(shoppingList.getName());
        output.setIngredients(ShoppingListUtil.getAllIngredients(shoppingList));

        return ResponseEntity.ok(output);
    }

    @GetMapping
    public ResponseEntity<List<GetAllShoppingListsResponse>> getAllShoppingLists() {
        List<GetAllShoppingListsResponse> outputList = shoppingListService.findAll().stream()
                .map(shoppingList -> {
                    GetAllShoppingListsResponse output = new GetAllShoppingListsResponse();
                    output.setShoppingListId(shoppingList.getShoppingListId());
                    output.setName(shoppingList.getName());
                    output.setIngredients(ShoppingListUtil.getAllIngredients(shoppingList));
                    return output;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(outputList);
    }

}
