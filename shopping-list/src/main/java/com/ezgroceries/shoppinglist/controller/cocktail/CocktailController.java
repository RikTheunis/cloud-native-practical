package com.ezgroceries.shoppinglist.controller.cocktail;

import com.ezgroceries.shoppinglist.controller.cocktail.contract.SearchCocktailResponse;
import com.ezgroceries.shoppinglist.service.cocktail.CocktailService;
import com.ezgroceries.shoppinglist.service.cocktail.model.Cocktail;
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

    public CocktailController(CocktailService cocktailService) {
        this.cocktailService = cocktailService;
    }

    @GetMapping
    public ResponseEntity<List<SearchCocktailResponse>> search(@RequestParam String search) {
        List<Cocktail> cocktails = cocktailService.searchCocktails(search);

        List<SearchCocktailResponse> outputList = cocktails.stream()
                .map(cocktail -> {
                    SearchCocktailResponse output = new SearchCocktailResponse();

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
