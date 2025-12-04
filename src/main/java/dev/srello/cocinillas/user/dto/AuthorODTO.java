package dev.srello.cocinillas.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthorODTO {
    private String username;
    private Boolean isUserAuthor;
}
