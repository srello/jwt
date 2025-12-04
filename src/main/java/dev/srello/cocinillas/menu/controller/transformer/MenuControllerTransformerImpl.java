package dev.srello.cocinillas.menu.controller.transformer;

import dev.srello.cocinillas.menu.dto.*;
import dev.srello.cocinillas.menu.rdto.*;
import dev.srello.cocinillas.user.dto.UserODTO;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class MenuControllerTransformerImpl implements MenuControllerTransformer {

    private final MenuControllerMapper mapper;

    @Override
    public MenuIDTO toMenuIDTO(@NonNull MenuRQRDTO menuRQRDTO, @NonNull UserODTO author) {
        return mapper.toMenuIDTO(menuRQRDTO, author);
    }

    @Override
    public MenuRSRDTO toMenuRSRDTO(@NonNull MenuODTO menuODTO) {
        return mapper.toMenuRSRDTO(menuODTO);
    }

    @Override
    public GetMenusIDTO toGetMenusIDTO(GetMenusRQRDTO getMenusRQRDTO, Long userId) {
        return mapper.toGetMenusIDTO(getMenusRQRDTO, userId);
    }

    @Override
    public Page<MenuRSRDTO> toMenusRSRDTO(Page<MenuODTO> menusODTO) {
        return menusODTO.map(this::toMenuRSRDTO);
    }

    @Override
    public Page<MenuSummaryRSRDTO> toMenuSummaryRSRDTO(@NonNull Page<MenuODTO> menusODTO) {
        return menusODTO.map(mapper::toMenuSummaryRSRDTO);
    }

    @Override
    public MenuInteractionIDTO toMenuInteractionIDTO(@NonNull MenuInteractionRQRDTO menuInteractionRQRDTO, @NonNull Long userId) {
        return mapper.toMenuInteractionIDTO(menuInteractionRQRDTO, userId);
    }

    @Override
    public MenuInteractionRSRDTO toMenuInteractionRSRDTO(@NonNull MenuInteractionODTO menuInteractionODTO) {
        return mapper.toMenuInteractionRSRDTO(menuInteractionODTO);
    }

    @Override
    public GetMenuIDTO toGetMenuIDTO(@NonNull Long menuId, @NonNull Long userId) {
        return mapper.toGetMenuIDTO(menuId, userId);
    }

    @Override
    public DeleteMenuIDTO toDeleteMenuIDTO(@NonNull Long menuId, @NonNull Long userId) {
        return mapper.toDeleteMenuIDTO(menuId, userId);
    }
}
