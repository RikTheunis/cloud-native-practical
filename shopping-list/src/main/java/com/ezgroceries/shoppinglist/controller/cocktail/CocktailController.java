package com.ezgroceries.shoppinglist.controller.cocktail;

import com.ezgroceries.shoppinglist.external.client.CocktailDBClient;
import com.ezgroceries.shoppinglist.external.contract.CocktailDBSearchOutputContract;
import com.ezgroceries.shoppinglist.service.cocktail.CocktailService;
import com.ezgroceries.shoppinglist.service.cocktail.model.CocktailResource;
import com.ezgroceries.shoppinglist.controller.cocktail.contract.SearchCocktailOutputContract;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/cocktails", produces = "application/json")
public class CocktailController {

    private final CocktailService cocktailService;
    private final CocktailDBClient cocktailDBClient;

    public CocktailController(CocktailService cocktailService, CocktailDBClient cocktailDBClient) {
        this.cocktailService = cocktailService;
        this.cocktailDBClient = cocktailDBClient;
    }

    @GetMapping
    public ResponseEntity<List<SearchCocktailOutputContract>> get(@RequestParam String search) {
        CocktailDBSearchOutputContract cocktailDBSearchOutput = cocktailDBClient.searchCocktails(search);

        List<CocktailResource> cocktailResources = cocktailService.mergeCocktails(cocktailDBSearchOutput.getDrinks());
        List<SearchCocktailOutputContract> outputList = cocktailResources.stream()
                .map(cocktail -> {
                    SearchCocktailOutputContract output = new SearchCocktailOutputContract();

                    output.setCocktailId(cocktail.getCocktailId());
                    output.setName(cocktail.getName());
                    output.setGlass(cocktail.getGlass());
                    output.setImage(cocktail.getImageUrl());
                    output.setInstructions(cocktail.getInstructions());
                    output.setIngredients(cocktail.getIngredients());

                    return output;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(outputList);
    }

}
