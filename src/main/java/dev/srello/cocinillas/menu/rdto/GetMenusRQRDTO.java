package dev.srello.cocinillas.menu.rdto;

import java.util.List;

public record GetMenusRQRDTO(
        String name,
        List<String> tags
) {
}
