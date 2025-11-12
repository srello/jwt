package dev.srello.cocinillas.menu.controller.transformer;

import dev.srello.cocinillas.menu.dto.GetMenusIDTO;
import dev.srello.cocinillas.menu.dto.MenuIDTO;
import dev.srello.cocinillas.menu.dto.MenuODTO;
import dev.srello.cocinillas.menu.rdto.GetMenusRQRDTO;
import dev.srello.cocinillas.menu.rdto.MenuRQRDTO;
import dev.srello.cocinillas.menu.rdto.MenuRSRDTO;
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
}
