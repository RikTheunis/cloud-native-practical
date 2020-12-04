package com.ezgroceries.shoppinglist.external.client;

import com.ezgroceries.shoppinglist.external.contract.CocktailDBSearchOutputContract;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(name = "cocktailDBClient", url = "https://www.thecocktaildb.com/api/json/v1/1")
public interface CocktailDBClient {

    @GetMapping(value = "search.php", produces = "application/json")
    CocktailDBSearchOutputContract searchCocktails(@RequestParam("s") String search);

}
