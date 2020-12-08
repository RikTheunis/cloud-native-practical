package com.ezgroceries.shoppinglist.repository.cocktail;

import com.ezgroceries.shoppinglist.repository.cocktail.entity.CocktailEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CocktailRepository extends JpaRepository<CocktailEntity, UUID> {

    List<CocktailEntity> findByIdDrinkIn(List<String> ids);

    List<CocktailEntity> findByNameContainingIgnoreCase(String search);
}
