package dev.srello.cocinillas.menu.service;

import dev.srello.cocinillas.menu.dto.*;
import dev.srello.cocinillas.shared.pagination.dto.PaginationIDTO;
import org.springframework.data.domain.Page;

public interface MenuService {
    MenuODTO createMenu(MenuIDTO menuIDTO);

    MenuODTO deleteMenu(DeleteMenuIDTO deleteMenuIDTO);

    Page<MenuODTO> getMenusPaginated(GetMenusIDTO getMenusIDTO, PaginationIDTO paginationIDTO);

    Page<MenuODTO> getUserMenusPaginated(GetMenusIDTO id, PaginationIDTO paginationIDTO);

    MenuInteractionODTO createMenuInteraction(MenuInteractionIDTO menuInteractionIDTO);

    MenuInteractionODTO deleteMenuInteraction(MenuInteractionIDTO menuInteractionIDTO);

    MenuODTO getMenuById(GetMenuIDTO getMenuIDTO);
}
