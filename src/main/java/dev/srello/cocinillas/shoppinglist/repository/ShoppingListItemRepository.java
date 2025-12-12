package dev.srello.cocinillas.shoppinglist.repository;

import dev.srello.cocinillas.shoppinglist.model.ShoppingListItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingListItemRepository extends JpaRepository<ShoppingListItem, Long> {
}
