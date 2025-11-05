package dev.srello.cocinillas.menu.repository;

import dev.srello.cocinillas.menu.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {
}
