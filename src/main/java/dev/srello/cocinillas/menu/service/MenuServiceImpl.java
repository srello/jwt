package dev.srello.cocinillas.menu.service;

import dev.srello.cocinillas.core.exception.custom.RequestException;
import dev.srello.cocinillas.menu.dto.*;
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

import static dev.srello.cocinillas.core.codes.messages.Codes.Error.*;
import static dev.srello.cocinillas.core.messages.Messages.Error.*;
import static dev.srello.cocinillas.shared.enums.InteractionType.LIKE;
import static dev.srello.cocinillas.shared.enums.Visibility.PRIVATE;
import static java.util.Collections.emptyList;
import static java.util.List.of;
import static java.util.Objects.isNull;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
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
            return transformer.toMenuODTO(savedMenu, interactions, recipes, menuIDTO.getAuthor().getId());
        } catch (DataIntegrityViolationException e) {
            throw new RequestException(NOT_FOUND, TAG_NOT_FOUND, TAG_NOT_FOUND_CODE);
        }
    }

    @Override
    public MenuODTO deleteMenu(DeleteMenuIDTO deleteMenuIDTO) {
        Menu menu = repository.findById(deleteMenuIDTO.getMenuId()).orElseThrow(() -> new RequestException(NOT_FOUND, MENU_NOT_FOUND, MENU_NOT_FOUND_CODE));
        if (!deleteMenuIDTO.getUserId().equals(menu.getAuthor().getId()))
            throw new RequestException(BAD_REQUEST, RESOURCE_DELETION_NOT_ALLOWED, RESOURCE_DELETION_NOT_ALLOWED_CODE);
        repository.delete(menu);
        return transformer.toMenuODTO(menu, emptyList(), emptyList(), deleteMenuIDTO.getUserId());
    }

    @Override
    public Page<MenuODTO> getMenusPaginated(GetMenusIDTO getMenusIDTO, PaginationIDTO paginationIDTO) {
        Long userId = getMenusIDTO.getUserId();
        Specification<Menu> menuSpecification = specificationService.buildMenusPaginatedSpecification(getMenusIDTO);
        Page<Menu> menus = repository.findAll(menuSpecification, paginationIDTO.getPageRequest());
        List<MenuInteraction> menuInteractions = getMenuInteractionsList(menus.toList(), userId);
        List<RecipeODTO> recipes = recipeServiceAdapter.getRecipesFromMenus(menus.toList(), userId);

        return transformer.toMenusODTO(menus, menuInteractions, recipes, getMenusIDTO.getUserId());
    }

    @Override
    public Page<MenuODTO> getUserMenusPaginated(GetMenusIDTO getMenusIDTO, PaginationIDTO paginationIDTO) {
        Long userId = getMenusIDTO.getUserId();
        Specification<Menu> userSpecification = specificationService.buildUserMenusPaginatedSpecification(getMenusIDTO);
        Page<Menu> menus = repository.findAll(userSpecification, paginationIDTO.getPageRequest());
        List<MenuInteraction> menuInteractions = getMenuInteractionsList(menus.toList(), userId);
        List<RecipeODTO> recipes = recipeServiceAdapter.getRecipesFromMenus(menus.toList(), userId);
        return transformer.toMenusODTO(menus, menuInteractions, recipes, getMenusIDTO.getUserId());
    }

    @Override
    public MenuInteractionODTO createMenuInteraction(MenuInteractionIDTO menuInteractionIDTO) {
        menuInteractionRepository.findByUserIdAndMenuIdAndType(menuInteractionIDTO.getUserId(), menuInteractionIDTO.getMenuId(),
                menuInteractionIDTO.getType()).ifPresent(ignored -> {
            throw new RequestException(BAD_REQUEST, MENU_INTERACTION_ALREADY_EXISTS, MENU_INTERACTION_ALREADY_EXISTS_CODE);
        });

        MenuInteraction menuInteraction = transformer.toMenuInteraction(menuInteractionIDTO);
        MenuInteraction savedMenuInteraction = menuInteractionRepository.saveAndFlush(menuInteraction);

        changeMenuLikes(savedMenuInteraction, 1L);

        return transformer.toMenuInteractionODTO(savedMenuInteraction);
    }

    @Override
    public MenuInteractionODTO deleteMenuInteraction(MenuInteractionIDTO menuInteractionIDTO) {
        MenuInteraction menuInteraction = menuInteractionRepository.findByUserIdAndMenuIdAndType(menuInteractionIDTO.getUserId(), menuInteractionIDTO.getMenuId(),
                menuInteractionIDTO.getType()).orElseThrow(() ->
                new RequestException(BAD_REQUEST, MENU_INTERACTION_DOES_NOT_EXIST, MENU_INTERACTION_DOES_NOT_EXIST_CODE));

        menuInteractionRepository.delete(menuInteraction);

        changeMenuLikes(menuInteraction, -1L);

        return transformer.toMenuInteractionODTO(menuInteraction);
    }

    @Override
    public MenuODTO getMenuById(GetMenuIDTO getMenuIDTO) {
        Menu menu = repository.findById(getMenuIDTO.getMenuId())
                .orElseThrow(() -> new RequestException(NOT_FOUND, MENU_NOT_FOUND, MENU_NOT_FOUND_CODE));

        Long userId = getMenuIDTO.getUserId();
        if (PRIVATE.equals(menu.getVisibility()) && !userId.equals(menu.getAuthor().getId()))
            throw new RequestException(BAD_REQUEST, RESOURCE_ACCESS_NOT_ALLOWED, RESOURCE_ACCESS_NOT_ALLOWED_CODE);

        List<MenuInteraction> menuInteractions = getMenuInteractionsList(of(menu), userId);
        List<RecipeODTO> recipes = recipeServiceAdapter.getRecipesFromMenus(of(menu), userId);

        return transformer.toMenuODTO(menu, menuInteractions, recipes, getMenuIDTO.getUserId());
    }

    private void changeMenuLikes(MenuInteraction menuInteraction, Long likesToAdd) {
        if (!LIKE.equals(menuInteraction.getType())) return;

        Menu menu = repository.findById(menuInteraction.getMenuId()).orElseThrow(() -> new RequestException(NOT_FOUND, MENU_NOT_FOUND, MENU_NOT_FOUND_CODE));
        menu.setLikes(menu.getLikes() + likesToAdd);
        repository.saveAndFlush(menu);
    }

    private List<MenuInteraction> getMenuInteractionsList(List<Menu> menus, Long userId) {
        if (isNull(userId))
            return emptyList();

        List<Long> menuIds = menus.stream().map(Menu::getId).toList();
        return menuInteractionRepository.findAllByUserIdAndMenuIdIn(userId, menuIds);
    }
}
