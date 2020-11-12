package com.ezgroceries.shoppinglist.controller;

import com.ezgroceries.shoppinglist.contract.SearchCocktailOutputContract;
import com.ezgroceries.shoppinglist.executor.SearchCocktailExecutor;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/cocktails", produces = "application/json")
public class CocktailController {

    @GetMapping
    public ResponseEntity<List<SearchCocktailOutputContract>> get(@RequestParam String search) {

        return new SearchCocktailExecutor().invoke(search);
    }

}
