package com.ezgroceries.shoppinglist.controller;

import com.ezgroceries.shoppinglist.backend.client.CocktailDBClient;
import com.ezgroceries.shoppinglist.backend.contract.CocktailDBSearchOutputContract;
import com.ezgroceries.shoppinglist.backend.contract.DrinkResource;
import com.ezgroceries.shoppinglist.contract.SearchCocktailOutputContract;
import com.ezgroceries.shoppinglist.executor.SearchCocktailExecutor;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/cocktails", produces = "application/json")
public class CocktailController {

    private final SearchCocktailExecutor searchCocktailExecutor;

    public CocktailController(SearchCocktailExecutor searchCocktailExecutor) {
        this.searchCocktailExecutor = searchCocktailExecutor;
    }

    @GetMapping
    public ResponseEntity<List<SearchCocktailOutputContract>> get(@RequestParam String search) {
        return searchCocktailExecutor.invoke(search);
    }

}
