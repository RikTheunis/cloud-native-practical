package com.ezgroceries.shoppinglist.repository.shoppinglist.entity;

import com.ezgroceries.shoppinglist.repository.cocktail.entity.CocktailEntity;
import java.util.Set;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "shopping_list")
public class ShoppingListEntity {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    private String ownerName;

    @ManyToMany()
    @JoinTable(
            name = "cocktail_shopping_list",
            joinColumns = @JoinColumn(name = "shopping_list_id"),
            inverseJoinColumns = @JoinColumn(name = "cocktail_id")
    )
    private Set<CocktailEntity> cocktails;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public Set<CocktailEntity> getCocktails() {
        return cocktails;
    }

    public void setCocktails(Set<CocktailEntity> cocktailEntities) {
        this.cocktails = cocktailEntities;
    }
}
