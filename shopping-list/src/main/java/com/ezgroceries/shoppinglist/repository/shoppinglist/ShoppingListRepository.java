package com.ezgroceries.shoppinglist.repository.shoppinglist;

import com.ezgroceries.shoppinglist.repository.shoppinglist.entity.ShoppingList;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingListRepository extends JpaRepository<ShoppingList, UUID> {



}
