package dev.srello.cocinillas.menu.service.transformer;

import dev.srello.cocinillas.menu.dto.MenuIDTO;
import dev.srello.cocinillas.menu.dto.MenuODTO;
import dev.srello.cocinillas.menu.model.Menu;
import dev.srello.cocinillas.menu.model.MenuInteraction;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

import static dev.srello.cocinillas.menu.enums.MenuInteractionType.LIKE;
import static dev.srello.cocinillas.menu.enums.MenuInteractionType.SAVE;


@Component
@RequiredArgsConstructor
public class MenuServiceTransformerImpl implements MenuServiceTransformer {
    private final MenuServiceMapper mapper;

    @Override
    public Menu toMenu(@NonNull MenuIDTO menuIDTO) {
        return mapper.toMenu(menuIDTO);
    }

    @Override
    public MenuODTO toMenuODTO(@NonNull Menu menu, @NonNull List<MenuInteraction> menuInteractions) {
        List<MenuInteraction> filteredInteractions = menuInteractions.stream()
                .filter(menuInteraction -> menuInteraction.getMenuId().equals(menu.getId()))
                .toList();
        Boolean isLiked = filteredInteractions.stream().anyMatch(menuInteraction -> menuInteraction.getType().equals(LIKE));
        Boolean isSaved = filteredInteractions.stream().anyMatch(menuInteraction -> menuInteraction.getType().equals(SAVE));
        return mapper.toMenuODTO(menu, isLiked, isSaved);
    }

    @Override
    public Page<MenuODTO> toMenusODTO(@NonNull Page<Menu> menus, @NonNull List<MenuInteraction> menuInteractions) {

        return menus.map(menu -> toMenuODTO(menu, menuInteractions));
    }
}
