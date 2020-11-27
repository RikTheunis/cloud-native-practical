package com.ezgroceries.shoppinglist.data;

import com.ezgroceries.shoppinglist.data.entity.ShoppingList;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingListRepository extends JpaRepository<ShoppingList, UUID> {



}
