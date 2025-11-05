package dev.srello.cocinillas.menu.service.transformer;

import dev.srello.cocinillas.menu.dto.MenuIDTO;
import dev.srello.cocinillas.menu.dto.MenuODTO;
import dev.srello.cocinillas.menu.model.Menu;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class MenuServiceTransformerImpl implements MenuServiceTransformer {
    private final MenuServiceMapper mapper;

    @Override
    public Menu toMenu(@NonNull MenuIDTO menuIDTO) {
        return mapper.toMenu(menuIDTO);
    }

    @Override
    public MenuODTO toMenuODTO(@NonNull Menu menu) {
        return mapper.toMenuODTO(menu);
    }
}
