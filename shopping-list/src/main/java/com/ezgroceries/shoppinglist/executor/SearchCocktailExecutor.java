package com.ezgroceries.shoppinglist.executor;

import com.ezgroceries.shoppinglist.external.client.CocktailDBClient;
import com.ezgroceries.shoppinglist.external.contract.CocktailDBSearchOutputContract;
import com.ezgroceries.shoppinglist.web.contract.SearchCocktailOutputContract;
import com.ezgroceries.shoppinglist.service.model.CocktailResource;
import com.ezgroceries.shoppinglist.service.CocktailService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class SearchCocktailExecutor {

    private final CocktailService cocktailService;
    private final CocktailDBClient cocktailDBClient;

    public SearchCocktailExecutor(CocktailService cocktailService, CocktailDBClient cocktailDBClient) {
        this.cocktailService = cocktailService;
        this.cocktailDBClient = cocktailDBClient;
    }

    public ResponseEntity<List<SearchCocktailOutputContract>> invoke(String search) {
        CocktailDBSearchOutputContract cocktailDBSearchOutput = cocktailDBClient.searchCocktails(search);

        List<CocktailResource> cocktailResources = cocktailService.mergeCocktails(cocktailDBSearchOutput.getDrinks());
        List<SearchCocktailOutputContract> outputList = cocktailResources.stream()
                .map(SearchCocktailExecutor::resourceToOutputContract)
                .collect(Collectors.toList());

        return ResponseEntity.ok(outputList);
    }

    private static SearchCocktailOutputContract resourceToOutputContract(CocktailResource cocktailResource) {
        SearchCocktailOutputContract output = new SearchCocktailOutputContract();

        output.setCocktailId(cocktailResource.getCocktailId());
        output.setName(cocktailResource.getName());
        output.setGlass(cocktailResource.getGlass());
        output.setImage(cocktailResource.getImageUrl());
        output.setInstructions(cocktailResource.getInstructions());
        output.setIngredients(cocktailResource.getIngredients());

        return output;
    }

}
