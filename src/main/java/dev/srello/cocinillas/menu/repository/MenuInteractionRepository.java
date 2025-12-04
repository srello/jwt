package dev.srello.cocinillas.menu.repository;

import dev.srello.cocinillas.menu.model.MenuInteraction;
import dev.srello.cocinillas.shared.enums.InteractionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MenuInteractionRepository extends JpaRepository<MenuInteraction, Long> {
    Optional<MenuInteraction> findByUserIdAndMenuIdAndType(Long userId, Long menuId, InteractionType type);

    List<MenuInteraction> findAllByUserIdAndMenuIdIn(Long userId, List<Long> menuIds);
}
