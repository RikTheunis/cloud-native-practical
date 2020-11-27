package com.ezgroceries.shoppinglist.service.shoppinglist;

import com.ezgroceries.shoppinglist.service.cocktail.model.CocktailResource;
import com.ezgroceries.shoppinglist.service.shoppinglist.model.ShoppingListResource;
import com.ezgroceries.shoppinglist.repository.cocktail.entity.Cocktail;
import com.ezgroceries.shoppinglist.repository.shoppinglist.entity.ShoppingList;
import com.ezgroceries.shoppinglist.repository.cocktail.CocktailRepository;
import com.ezgroceries.shoppinglist.repository.shoppinglist.ShoppingListRepository;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ShoppingListService {

    private final ShoppingListRepository shoppingListRepository;
    private final CocktailRepository cocktailRepository;

    public ShoppingListService(ShoppingListRepository shoppingListRepository, CocktailRepository cocktailRepository) {
        this.shoppingListRepository = shoppingListRepository;
        this.cocktailRepository = cocktailRepository;
    }

    public ShoppingListResource createEmpty(String name) {
        ShoppingList shoppingList = new ShoppingList();
        shoppingList.setName(name);

        shoppingList = shoppingListRepository.save(shoppingList);

        return new ShoppingListResource(
                shoppingList.getId(),
                shoppingList.getName()
        );
    }

    public ShoppingListResource addCocktailsToShoppingList(UUID shoppingListId, List<UUID> cocktailIds) {
        ShoppingList shoppingList = shoppingListRepository.getOne(shoppingListId);

        List<Cocktail> cocktailsToBeAdded = cocktailIds.stream().map(cocktailId -> cocktailRepository.getOne(cocktailId)).collect(Collectors.toList());

        Set<Cocktail> cocktails = shoppingList.getCocktails();
        cocktails.addAll(cocktailsToBeAdded);
        shoppingList.setCocktails(cocktails);

        shoppingList = shoppingListRepository.save(shoppingList);

        return new ShoppingListResource(
                shoppingList.getId(),
                shoppingList.getName(),
                shoppingList.getCocktails().stream().map(cocktail1 -> new CocktailResource(
                            cocktail1.getId(),
                            cocktail1.getName(),
                            "",
                            "",
                            "",
                            cocktail1.getIngredients()
                    )).collect(Collectors.toSet())
        );
    }

    public ShoppingListResource getShoppingList(UUID shoppingListId) {
        ShoppingList shoppingList = shoppingListRepository.getOne(shoppingListId);

        return new ShoppingListResource(
                shoppingList.getId(),
                shoppingList.getName(),
                shoppingList.getCocktails().stream().map(cocktail1 -> new CocktailResource(
                        cocktail1.getId(),
                        cocktail1.getName(),
                        "",
                        "",
                        "",
                        cocktail1.getIngredients()
                )).collect(Collectors.toSet())
        );
    }

    public List<ShoppingListResource> findAll() {
        List<ShoppingList> shoppingLists = shoppingListRepository.findAll();

        return shoppingLists.stream()
                .map(shoppingList -> new ShoppingListResource(
                        shoppingList.getId(),
                        shoppingList.getName(),
                        shoppingList.getCocktails().stream().map(cocktail1 -> new CocktailResource(
                                cocktail1.getId(),
                                cocktail1.getName(),
                                "",
                                "",
                                "",
                                cocktail1.getIngredients()
                        )).collect(Collectors.toSet())
                ))
                .collect(Collectors.toList());
    }
}
