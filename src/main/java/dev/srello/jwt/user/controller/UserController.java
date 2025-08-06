package dev.srello.jwt.user.controller;

import dev.srello.jwt.user.CurrentUser;
import dev.srello.jwt.user.controller.transformer.UserControllerTransformer;
import dev.srello.jwt.user.dto.UserODTO;
import dev.srello.jwt.user.rdto.UserRSRDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserControllerTransformer userControllerTransformer;

    @GetMapping("/me")
    public ResponseEntity<UserRSRDTO> getMyUser(@CurrentUser UserODTO userODTO){
        UserRSRDTO userRSRDTO = userControllerTransformer.toRSRDTO(userODTO);

        return ResponseEntity.ok().body(userRSRDTO);
    }
}
