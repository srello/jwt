package dev.srello.cocinillas.menu.controller.transformer;

import dev.srello.cocinillas.menu.dto.*;
import dev.srello.cocinillas.menu.rdto.*;
import dev.srello.cocinillas.user.dto.UserODTO;
import lombok.NonNull;
import org.springframework.data.domain.Page;


public interface MenuControllerTransformer {
    MenuIDTO toMenuIDTO(@NonNull MenuRQRDTO menuRQRDTO, @NonNull UserODTO author);

    MenuRSRDTO toMenuRSRDTO(@NonNull MenuODTO menuODTO);

    GetMenusIDTO toGetMenusIDTO(@NonNull GetMenusRQRDTO getMenusRQRDTO, Long userId);

    Page<MenuRSRDTO> toMenusRSRDTO(@NonNull Page<MenuODTO> menusODTO);

    Page<MenuSummaryRSRDTO> toMenuSummaryRSRDTO(@NonNull Page<MenuODTO> menusODTO);

    MenuInteractionIDTO toMenuInteractionIDTO(@NonNull MenuInteractionRQRDTO menuInteractionRQRDTO, @NonNull Long userId);

    MenuInteractionRSRDTO toMenuInteractionRSRDTO(@NonNull MenuInteractionODTO menuInteractionODTO);

    GetMenuIDTO toGetMenuIDTO(@NonNull Long menuId, @NonNull Long userId);

    DeleteMenuIDTO toDeleteMenuIDTO(@NonNull Long menuId, @NonNull Long userId);
}
