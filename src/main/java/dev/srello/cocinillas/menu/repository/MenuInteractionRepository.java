package dev.srello.cocinillas.menu.repository;

import dev.srello.cocinillas.menu.enums.MenuInteractionType;
import dev.srello.cocinillas.menu.model.MenuInteraction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MenuInteractionRepository extends JpaRepository<MenuInteraction, Long> {
    Optional<MenuInteraction> findByUserIdAndMenuIdAndType(Long userId, Long menuId, MenuInteractionType type);

    List<MenuInteraction> findAllByUserIdAndMenuIdIn(Long userId, List<Long> menuIds);
}
