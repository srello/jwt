package dev.srello.cocinillas.menu.service.transformer;

import dev.srello.cocinillas.menu.dto.MenuIDTO;
import dev.srello.cocinillas.menu.dto.MenuODTO;
import dev.srello.cocinillas.menu.model.Menu;
import dev.srello.cocinillas.menu.model.MenuInteraction;
import lombok.NonNull;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MenuServiceTransformer {
    Menu toMenu(@NonNull MenuIDTO menuIDTO);

    MenuODTO toMenuODTO(@NonNull Menu menu, @NonNull List<MenuInteraction> menuInteractions);

    Page<MenuODTO> toMenusODTO(@NonNull Page<Menu> menus, @NonNull List<MenuInteraction> menuInteractions);
}
