package dev.srello.cocinillas.settings.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;


@Getter
@AllArgsConstructor
public class SettingsODTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private Integer defaultDiners;
}
