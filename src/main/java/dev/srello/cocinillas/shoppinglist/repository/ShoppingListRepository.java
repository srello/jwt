package dev.srello.cocinillas.shoppinglist.repository;

import dev.srello.cocinillas.shoppinglist.model.ShoppingList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingListRepository extends JpaRepository<ShoppingList, Long> {
    Page<ShoppingList> findAllByAuthorId(Long authorId, Pageable pageable);
}
