package dev.srello.cocinillas.menu.controller;

import dev.srello.cocinillas.menu.controller.transformer.MenuControllerTransformer;
import dev.srello.cocinillas.menu.dto.GetMenusIDTO;
import dev.srello.cocinillas.menu.dto.MenuIDTO;
import dev.srello.cocinillas.menu.dto.MenuODTO;
import dev.srello.cocinillas.menu.rdto.GetMenusRQRDTO;
import dev.srello.cocinillas.menu.rdto.MenuRQRDTO;
import dev.srello.cocinillas.menu.rdto.MenuRSRDTO;
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

import static dev.srello.cocinillas.core.request.RequestConstants.ID_PATH_VARIABLE;
import static org.springframework.http.ResponseEntity.ok;


@Controller
@RequiredArgsConstructor
@RequestMapping("/menus")
public class MenuController {

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
    public ResponseEntity<MenuRSRDTO> deleteMenu(@PathVariable Long id) {
        MenuODTO menuODTO = service.deleteMenu(id);
        MenuRSRDTO menuRSRDTO = transformer.toMenuRSRDTO(menuODTO);
        return ok().body(menuRSRDTO);
    }

    @GetMapping
    public ResponseEntity<Page<MenuRSRDTO>> getMenusPaginated(@Valid GetMenusRQRDTO getMenusRQRDTO, @Valid PaginationRQRDTO paginationRQRDTO, @CurrentUser UserODTO currentUser) {
        GetMenusIDTO getMenusIDTO = transformer.toGetMenusIDTO(getMenusRQRDTO, currentUser.getId());
        PaginationIDTO paginationIDTO = paginationTransformer.toPaginationIDTO(paginationRQRDTO);
        Page<MenuODTO> menusODTO = service.getMenusPaginated(getMenusIDTO, paginationIDTO);
        Page<MenuRSRDTO> menusRSRDTO = transformer.toMenusRSRDTO(menusODTO);
        return ok().body(menusRSRDTO);
    }

    @GetMapping("/private")
    public ResponseEntity<Page<MenuRSRDTO>> getUserMenusPaginated(@Valid PaginationRQRDTO paginationRQRDTO, @CurrentUser UserODTO currentUser) {
        PaginationIDTO paginationIDTO = paginationTransformer.toPaginationIDTO(paginationRQRDTO);
        Page<MenuODTO> menusODTO = service.getUserMenusPaginated(currentUser.getId(), paginationIDTO);
        Page<MenuRSRDTO> menusRSRDTO = transformer.toMenusRSRDTO(menusODTO);
        return ok().body(menusRSRDTO);
    }
}
