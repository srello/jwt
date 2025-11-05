package dev.srello.cocinillas.menu.service;

import dev.srello.cocinillas.core.exception.custom.RequestException;
import dev.srello.cocinillas.menu.dto.MenuIDTO;
import dev.srello.cocinillas.menu.dto.MenuODTO;
import dev.srello.cocinillas.menu.model.Menu;
import dev.srello.cocinillas.menu.repository.MenuRepository;
import dev.srello.cocinillas.menu.service.transformer.MenuServiceTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static dev.srello.cocinillas.core.codes.messages.Codes.Error.TAG_NOT_FOUND_CODE;
import static dev.srello.cocinillas.core.messages.Messages.Error.TAG_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuRepository repository;
    private final MenuServiceTransformer transformer;

    @Override
    public MenuODTO createMenu(MenuIDTO menuIDTO) {
        Menu menu = transformer.toMenu(menuIDTO);
        try {
            Menu savedMenu = repository.save(menu);
            return transformer.toMenuODTO(savedMenu);
        } catch (DataIntegrityViolationException e) {
            throw new RequestException(HttpStatus.NOT_FOUND, TAG_NOT_FOUND, TAG_NOT_FOUND_CODE);
        }
    }
}
