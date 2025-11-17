package dev.srello.cocinillas.menu.service;

import dev.srello.cocinillas.core.exception.custom.RequestException;
import dev.srello.cocinillas.menu.dto.GetMenusIDTO;
import dev.srello.cocinillas.menu.dto.MenuIDTO;
import dev.srello.cocinillas.menu.dto.MenuODTO;
import dev.srello.cocinillas.menu.model.Menu;
import dev.srello.cocinillas.menu.model.MenuInteraction;
import dev.srello.cocinillas.menu.repository.MenuInteractionRepository;
import dev.srello.cocinillas.menu.repository.MenuRepository;
import dev.srello.cocinillas.menu.service.transformer.MenuServiceTransformer;
import dev.srello.cocinillas.menu.specification.MenuSpecificationService;
import dev.srello.cocinillas.recipe.adapter.RecipeServiceAdapter;
import dev.srello.cocinillas.recipe.dto.RecipeODTO;
import dev.srello.cocinillas.shared.pagination.dto.PaginationIDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

import static dev.srello.cocinillas.core.codes.messages.Codes.Error.MENU_NOT_FOUND_CODE;
import static dev.srello.cocinillas.core.codes.messages.Codes.Error.TAG_NOT_FOUND_CODE;
import static dev.srello.cocinillas.core.messages.Messages.Error.MENU_NOT_FOUND;
import static dev.srello.cocinillas.core.messages.Messages.Error.TAG_NOT_FOUND;
import static java.util.Collections.emptyList;
import static java.util.List.of;
import static java.util.Objects.isNull;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuRepository repository;
    private final MenuServiceTransformer transformer;
    private final MenuSpecificationService specificationService;
    private final MenuInteractionRepository menuInteractionRepository;
    private final RecipeServiceAdapter recipeServiceAdapter;

    @Override
    public MenuODTO createMenu(MenuIDTO menuIDTO) {
        Menu menu = transformer.toMenu(menuIDTO);
        try {
            Menu savedMenu = repository.save(menu);
            Long authorId = menuIDTO.getAuthor().getId();
            List<RecipeODTO> recipes = recipeServiceAdapter.getRecipesFromMenus(of(menu), authorId);
            List<MenuInteraction> interactions = getMenuInteractionsList(of(savedMenu), authorId);
            return transformer.toMenuODTO(savedMenu, interactions, recipes);
        } catch (DataIntegrityViolationException e) {
            throw new RequestException(NOT_FOUND, TAG_NOT_FOUND, TAG_NOT_FOUND_CODE);
        }
    }

    @Override
    public MenuODTO deleteMenu(Long id) {
        Menu menu = repository.findById(id).orElseThrow(() -> new RequestException(NOT_FOUND, MENU_NOT_FOUND, MENU_NOT_FOUND_CODE));
        repository.delete(menu);
        return transformer.toMenuODTO(menu, emptyList(), emptyList());
    }

    @Override
    public Page<MenuODTO> getMenusPaginated(GetMenusIDTO getMenusIDTO, PaginationIDTO paginationIDTO) {
        Long userId = getMenusIDTO.getUserId();
        Specification<Menu> menuSpecification = specificationService.buildMenusPaginatedSpecification(getMenusIDTO);
        Page<Menu> menus = repository.findAll(menuSpecification, paginationIDTO.getPageRequest());
        List<MenuInteraction> menuInteractions = getMenuInteractionsList(menus.toList(), userId);
        List<RecipeODTO> recipes = recipeServiceAdapter.getRecipesFromMenus(menus.toList(), userId);

        return transformer.toMenusODTO(menus, menuInteractions, recipes);
    }

    @Override
    public Page<MenuODTO> getUserMenusPaginated(GetMenusIDTO getMenusIDTO, PaginationIDTO paginationIDTO) {
        Long userId = getMenusIDTO.getUserId();
        Specification<Menu> userSpecification = specificationService.buildUserMenusPaginatedSpecification(getMenusIDTO);
        Page<Menu> menus = repository.findAll(userSpecification, paginationIDTO.getPageRequest());
        List<MenuInteraction> menuInteractions = getMenuInteractionsList(menus.toList(), userId);
        List<RecipeODTO> recipes = recipeServiceAdapter.getRecipesFromMenus(menus.toList(), userId);
        return transformer.toMenusODTO(menus, menuInteractions, recipes);
    }

    private List<MenuInteraction> getMenuInteractionsList(List<Menu> menus, Long userId) {
        if (isNull(userId))
            return emptyList();

        List<Long> menuIds = menus.stream().map(Menu::getId).toList();
        return menuInteractionRepository.findAllByUserIdAndMenuIdIn(userId, menuIds);
    }
}
