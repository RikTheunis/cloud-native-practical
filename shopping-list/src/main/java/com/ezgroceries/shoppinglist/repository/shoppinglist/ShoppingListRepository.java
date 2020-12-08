package com.ezgroceries.shoppinglist.repository.shoppinglist;

import com.ezgroceries.shoppinglist.repository.shoppinglist.entity.ShoppingListEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingListRepository extends JpaRepository<ShoppingListEntity, UUID> {

    List<ShoppingListEntity> findByOwnerName(String ownerName);

    ShoppingListEntity findFirstByIdAndOwnerName(UUID id, String ownerName);

}
