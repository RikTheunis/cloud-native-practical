package com.ezgroceries.shoppinglist.persistence.repository;

import com.ezgroceries.shoppinglist.persistence.entity.Cocktail;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CocktailRepository extends JpaRepository<Cocktail, UUID> {

    List<Cocktail> findByIdDrinkIn(List<String> ids);

}
