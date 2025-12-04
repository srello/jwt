package dev.srello.cocinillas.menu.controller;

import dev.srello.cocinillas.menu.controller.transformer.MenuControllerTransformer;
import dev.srello.cocinillas.menu.dto.*;
import dev.srello.cocinillas.menu.rdto.*;
import dev.srello.cocinillas.menu.service.MenuService;
import dev.srello.cocinillas.shared.pagination.dto.PaginationIDTO;
import dev.srello.cocinillas.shared.pagination.rdto.PaginationRQRDTO;
import dev.srello.cocinillas.shared.pagination.transformer.PaginationTransformer;
import dev.srello.cocinillas.user.CurrentUser;
import dev.srello.cocinillas.user.dto.UserODTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static dev.srello.cocinillas.core.request.RequestConstants.*;
import static org.springframework.http.ResponseEntity.ok;


@Controller
@RequiredArgsConstructor
@RequestMapping(MenuController.MENUS_ROUTE)
public class MenuController {

    public static final String MENUS_ROUTE = "/menus";
    private final MenuControllerTransformer transformer;
    private final PaginationTransformer paginationTransformer;
    private final MenuService service;

    @PostMapping
    public ResponseEntity<MenuRSRDTO> createMenu(@RequestBody MenuRQRDTO menuRQRDTO, @CurrentUser UserODTO currentUser) {
        MenuIDTO menuIDTO = transformer.toMenuIDTO(menuRQRDTO, currentUser);
        MenuODTO menuODTO = service.createMenu(menuIDTO);
        MenuRSRDTO menuRSRDTO = transformer.toMenuRSRDTO(menuODTO);
        return ok().body(menuRSRDTO);
    }

    @DeleteMapping(ID_PATH_VARIABLE)
    public ResponseEntity<MenuRSRDTO> deleteMenu(@PathVariable Long id, @CurrentUser UserODTO currentUser) {
        DeleteMenuIDTO deleteMenuIDTO = transformer.toDeleteMenuIDTO(id, currentUser.getId());
        MenuODTO menuODTO = service.deleteMenu(deleteMenuIDTO);
        MenuRSRDTO menuRSRDTO = transformer.toMenuRSRDTO(menuODTO);
        return ok().body(menuRSRDTO);
    }

    @GetMapping
    public ResponseEntity<Page<MenuSummaryRSRDTO>> getMenusPaginated(@Valid GetMenusRQRDTO getMenusRQRDTO, @Valid PaginationRQRDTO paginationRQRDTO, @CurrentUser UserODTO currentUser) {
        GetMenusIDTO getMenusIDTO = transformer.toGetMenusIDTO(getMenusRQRDTO, currentUser.getId());
        PaginationIDTO paginationIDTO = paginationTransformer.toPaginationIDTO(paginationRQRDTO);
        Page<MenuODTO> menusODTO = service.getMenusPaginated(getMenusIDTO, paginationIDTO);
        Page<MenuSummaryRSRDTO> menuSummaryRSRDTO = transformer.toMenuSummaryRSRDTO(menusODTO);
        return ok().body(menuSummaryRSRDTO);
    }

    @GetMapping(ME_PATH_VARIABLE)
    public ResponseEntity<Page<MenuSummaryRSRDTO>> getUserMenusPaginated(@Valid GetMenusRQRDTO getMenusRQRDTO, @Valid PaginationRQRDTO paginationRQRDTO, @CurrentUser UserODTO currentUser) {
        GetMenusIDTO getMenusIDTO = transformer.toGetMenusIDTO(getMenusRQRDTO, currentUser.getId());
        PaginationIDTO paginationIDTO = paginationTransformer.toPaginationIDTO(paginationRQRDTO);
        Page<MenuODTO> menusODTO = service.getUserMenusPaginated(getMenusIDTO, paginationIDTO);
        Page<MenuSummaryRSRDTO> menuSummaryRSRDTO = transformer.toMenuSummaryRSRDTO(menusODTO);
        return ok().body(menuSummaryRSRDTO);
    }

    @GetMapping(ID_PATH_VARIABLE)
    public ResponseEntity<MenuRSRDTO> getMenuById(@PathVariable Long id, @CurrentUser UserODTO currentUser) {
        GetMenuIDTO getMenuIDTO = transformer.toGetMenuIDTO(id, currentUser.getId());
        MenuODTO menuODTO = service.getMenuById(getMenuIDTO);
        MenuRSRDTO menuRSRDTO = transformer.toMenuRSRDTO(menuODTO);
        return ok(menuRSRDTO);
    }

    @PostMapping(INTERACTIONS_PATH)
    public ResponseEntity<MenuInteractionRSRDTO> createMenuInteraction(@RequestBody MenuInteractionRQRDTO menuInteractionRQRDTO, @CurrentUser UserODTO currentUser) {
        MenuInteractionIDTO menuInteractionIDTO = transformer.toMenuInteractionIDTO(menuInteractionRQRDTO, currentUser.getId());
        MenuInteractionODTO menuInteractionODTO = service.createMenuInteraction(menuInteractionIDTO);
        MenuInteractionRSRDTO menuInteractionRSRDTO = transformer.toMenuInteractionRSRDTO(menuInteractionODTO);
        return ok(menuInteractionRSRDTO);
    }

    @DeleteMapping(INTERACTIONS_PATH)
    public ResponseEntity<MenuInteractionRSRDTO> deleteMenuInteraction(@Valid MenuInteractionRQRDTO menuInteractionRQRDTO, @CurrentUser UserODTO currentUser) {
        MenuInteractionIDTO menuInteractionIDTO = transformer.toMenuInteractionIDTO(menuInteractionRQRDTO, currentUser.getId());
        MenuInteractionODTO menuInteractionODTO = service.deleteMenuInteraction(menuInteractionIDTO);
        MenuInteractionRSRDTO menuInteractionRSRDTO = transformer.toMenuInteractionRSRDTO(menuInteractionODTO);
        return ok(menuInteractionRSRDTO);
    }
}
