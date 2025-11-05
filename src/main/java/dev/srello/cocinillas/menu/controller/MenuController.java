package dev.srello.cocinillas.menu.controller;

import dev.srello.cocinillas.menu.controller.transformer.MenuControllerTransformer;
import dev.srello.cocinillas.menu.dto.MenuIDTO;
import dev.srello.cocinillas.menu.dto.MenuODTO;
import dev.srello.cocinillas.menu.rdto.MenuRQRDTO;
import dev.srello.cocinillas.menu.rdto.MenuRSRDTO;
import dev.srello.cocinillas.menu.service.MenuService;
import dev.srello.cocinillas.user.CurrentUser;
import dev.srello.cocinillas.user.dto.UserODTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.http.ResponseEntity.ok;


@Controller
@RequiredArgsConstructor
@RequestMapping("/menus")
public class MenuController {

    private final MenuControllerTransformer transformer;
    private final MenuService service;

    @PostMapping
    public ResponseEntity<MenuRSRDTO> createMenu(@RequestBody MenuRQRDTO menuRQRDTO, @CurrentUser UserODTO currentUser) {
        MenuIDTO menuIDTO = transformer.toMenuIDTO(menuRQRDTO, currentUser);
        MenuODTO menuODTO = service.createMenu(menuIDTO);
        MenuRSRDTO menuRSRDTO = transformer.toMenuRSRDTO(menuODTO);
        return ok().body(menuRSRDTO);
    }
}
