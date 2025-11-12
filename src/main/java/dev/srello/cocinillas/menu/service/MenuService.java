package dev.srello.cocinillas.menu.service;

import dev.srello.cocinillas.menu.dto.GetMenusIDTO;
import dev.srello.cocinillas.menu.dto.MenuIDTO;
import dev.srello.cocinillas.menu.dto.MenuODTO;
import dev.srello.cocinillas.shared.pagination.dto.PaginationIDTO;
import org.springframework.data.domain.Page;

public interface MenuService {
    MenuODTO createMenu(MenuIDTO menuIDTO);

    MenuODTO deleteMenu(Long id);

    Page<MenuODTO> getMenusPaginated(GetMenusIDTO getMenusIDTO, PaginationIDTO paginationIDTO);

    Page<MenuODTO> getUserMenusPaginated(Long id, PaginationIDTO paginationIDTO);
}
