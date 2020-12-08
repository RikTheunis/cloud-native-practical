package com.ezgroceries.shoppinglist.service.shoppinglist;

import com.ezgroceries.shoppinglist.repository.cocktail.CocktailRepository;
import com.ezgroceries.shoppinglist.repository.cocktail.entity.CocktailEntity;
import com.ezgroceries.shoppinglist.repository.shoppinglist.ShoppingListRepository;
import com.ezgroceries.shoppinglist.repository.shoppinglist.entity.ShoppingListEntity;
import com.ezgroceries.shoppinglist.service.cocktail.model.CocktailResource;
import com.ezgroceries.shoppinglist.service.shoppinglist.model.ShoppingListResource;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public class ShoppingListService {

    private final ShoppingListRepository shoppingListRepository;
    private final CocktailRepository cocktailRepository;

    public ShoppingListService(ShoppingListRepository shoppingListRepository, CocktailRepository cocktailRepository) {
        this.shoppingListRepository = shoppingListRepository;
        this.cocktailRepository = cocktailRepository;
    }

    @PreAuthorize("hasRole('USER') && #ownerName == principal.username")
    // PostAuthorize could be added
    // But PreAuthorize must be there because otherwise it is saved before authorization
    public ShoppingListResource createEmptyShoppingList(String shoppingListName, String ownerName) {
        ShoppingListEntity shoppingListEntity = new ShoppingListEntity();
        shoppingListEntity.setName(shoppingListName);
        shoppingListEntity.setOwnerName(ownerName);

        shoppingListEntity = shoppingListRepository.save(shoppingListEntity);

        return new ShoppingListResource(
                shoppingListEntity.getId(),
                shoppingListEntity.getName(),
                shoppingListEntity.getOwnerName()
        );
    }

    @PreAuthorize("hasRole('USER')"
            + "&& shoppingListRepository.getOne(#shoppingListId).ownerName == principal.username")
    // PostAuthorize could be added
    // But PreAuthorize must be there because otherwise it is saved before authorization
    public ShoppingListResource addCocktailsToShoppingList(UUID shoppingListId, List<UUID> cocktailIds) {
        ShoppingListEntity shoppingListEntity = shoppingListRepository.getOne(shoppingListId);

        List<CocktailEntity> cocktailsToBeAdded = cocktailIds.stream().map(cocktailId -> cocktailRepository.getOne(cocktailId)).collect(Collectors.toList());

        Set<CocktailEntity> cocktailEntities = shoppingListEntity.getCocktails();
        cocktailEntities.addAll(cocktailsToBeAdded);
        shoppingListEntity.setCocktails(cocktailEntities);

        shoppingListEntity = shoppingListRepository.save(shoppingListEntity);

        return new ShoppingListResource(
                shoppingListEntity.getId(),
                shoppingListEntity.getName(),
                shoppingListEntity.getOwnerName(),
                shoppingListEntity.getCocktails().stream().map(cocktail1 -> new CocktailResource(
                            cocktail1.getId(),
                            cocktail1.getName(),
                            "",
                            "",
                            "",
                            cocktail1.getIngredients()
                    )).collect(Collectors.toSet())
        );
    }

    // PostAuthorize is possible because only a get is performed
    // Don't know what is best practise though
    @PreAuthorize("hasRole('USER')")
    @PostAuthorize("returnObject.ownerName == principal.username")
    public ShoppingListResource getShoppingList(UUID shoppingListId) {
        ShoppingListEntity shoppingListEntity = shoppingListRepository.getOne(shoppingListId);

        return new ShoppingListResource(
                shoppingListEntity.getId(),
                shoppingListEntity.getName(),
                shoppingListEntity.getOwnerName(),
                shoppingListEntity.getCocktails().stream().map(cocktail1 -> new CocktailResource(
                        cocktail1.getId(),
                        cocktail1.getName(),
                        "",
                        "",
                        "",
                        cocktail1.getIngredients()
                )).collect(Collectors.toSet())
        );
    }

    @PreAuthorize("hasRole('USER')")
    @PostFilter("filterObject.ownerName == principal.username")
    public List<ShoppingListResource> findAll() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<ShoppingListEntity> shoppingListEntities = shoppingListRepository.findByOwnerName(user.getUsername());

        return shoppingListEntities.stream()
                .map(shoppingList -> new ShoppingListResource(
                        shoppingList.getId(),
                        shoppingList.getName(),
                        shoppingList.getOwnerName(),
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
