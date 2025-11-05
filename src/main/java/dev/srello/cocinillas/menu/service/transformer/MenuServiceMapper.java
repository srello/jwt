package dev.srello.cocinillas.menu.service.transformer;

import dev.srello.cocinillas.menu.dto.MenuIDTO;
import dev.srello.cocinillas.menu.dto.MenuODTO;
import dev.srello.cocinillas.menu.model.Menu;
import dev.srello.cocinillas.tags.service.transformer.TagServiceMapper;
import org.mapstruct.Mapper;

@Mapper(uses = TagServiceMapper.class)
public interface MenuServiceMapper {
    Menu toMenu(MenuIDTO menuIDTO);

    MenuODTO toMenuODTO(Menu menu);
}
