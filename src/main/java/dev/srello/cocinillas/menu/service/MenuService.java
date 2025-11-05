package dev.srello.cocinillas.menu.service;

import dev.srello.cocinillas.menu.dto.MenuIDTO;
import dev.srello.cocinillas.menu.dto.MenuODTO;

public interface MenuService {
    MenuODTO createMenu(MenuIDTO menuIDTO);
}
