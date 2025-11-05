package dev.srello.cocinillas.menu.service.transformer;

import dev.srello.cocinillas.menu.dto.MenuIDTO;
import dev.srello.cocinillas.menu.dto.MenuODTO;
import dev.srello.cocinillas.menu.model.Menu;
import lombok.NonNull;

public interface MenuServiceTransformer {
    Menu toMenu(@NonNull MenuIDTO menuIDTO);

    MenuODTO toMenuODTO(@NonNull Menu menu);
}
