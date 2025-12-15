package dev.srello.cocinillas.settings.rdto;

import org.jetbrains.annotations.NotNull;

public record SettingsRQRDTO(
        @NotNull("Default diners can not be null.")
        Integer defaultDiners
) {
}
