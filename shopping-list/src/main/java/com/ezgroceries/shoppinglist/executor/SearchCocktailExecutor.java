package com.ezgroceries.shoppinglist.executor;

import com.ezgroceries.shoppinglist.DummyData;
import com.ezgroceries.shoppinglist.contract.SearchCocktailOutputContract;
import com.ezgroceries.shoppinglist.model.CocktailResource;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;

public class SearchCocktailExecutor {

    public ResponseEntity<List<SearchCocktailOutputContract>> invoke(String search) {
        List<CocktailResource> cocktails = DummyData.getDummyCocktailResources();

        List<SearchCocktailOutputContract> outputList = cocktails
                .stream()
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
