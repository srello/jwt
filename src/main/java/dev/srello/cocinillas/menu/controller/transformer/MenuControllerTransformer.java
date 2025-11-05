package dev.srello.cocinillas.menu.controller.transformer;

import dev.srello.cocinillas.menu.dto.MenuIDTO;
import dev.srello.cocinillas.menu.dto.MenuODTO;
import dev.srello.cocinillas.menu.rdto.MenuRQRDTO;
import dev.srello.cocinillas.menu.rdto.MenuRSRDTO;
import dev.srello.cocinillas.user.dto.UserODTO;
import lombok.NonNull;


public interface MenuControllerTransformer {
    MenuIDTO toMenuIDTO(@NonNull MenuRQRDTO menuRQRDTO, @NonNull UserODTO author);

    MenuRSRDTO toMenuRSRDTO(@NonNull MenuODTO menuODTO);
}
