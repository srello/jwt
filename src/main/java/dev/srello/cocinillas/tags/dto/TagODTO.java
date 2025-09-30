package dev.srello.cocinillas.tags.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class TagODTO {

    private Long id;

    private String name;

    private List<TagTypeODTO> tagTypes;
}
