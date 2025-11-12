package dev.srello.cocinillas.menu.controller.transformer;

import dev.srello.cocinillas.menu.dto.GetMenusIDTO;
import dev.srello.cocinillas.menu.dto.MenuIDTO;
import dev.srello.cocinillas.menu.dto.MenuODTO;
import dev.srello.cocinillas.menu.rdto.GetMenusRQRDTO;
import dev.srello.cocinillas.menu.rdto.MenuRQRDTO;
import dev.srello.cocinillas.menu.rdto.MenuRSRDTO;
import dev.srello.cocinillas.tags.controller.transformer.TagControllerMapper;
import dev.srello.cocinillas.user.dto.UserODTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = TagControllerMapper.class)
public interface MenuControllerMapper {
    @Mapping(target = "name", source = "menu.name")
    MenuIDTO toMenuIDTO(MenuRQRDTO menu, UserODTO author);

    MenuRSRDTO toMenuRSRDTO(MenuODTO menuODTO);

    GetMenusIDTO toGetMenusIDTO(GetMenusRQRDTO getMenusRQRDTO, Long userId);
}
