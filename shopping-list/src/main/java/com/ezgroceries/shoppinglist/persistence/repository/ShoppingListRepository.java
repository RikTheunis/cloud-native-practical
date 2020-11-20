package com.ezgroceries.shoppinglist.persistence.repository;

import com.ezgroceries.shoppinglist.persistence.entity.ShoppingList;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingListRepository extends JpaRepository<ShoppingList, UUID> {



}
