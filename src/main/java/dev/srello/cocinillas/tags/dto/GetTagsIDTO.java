package dev.srello.cocinillas.tags.dto;

import dev.srello.cocinillas.tags.enums.TagType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class GetTagsIDTO {
    private String name;
    private List<TagType> types;
}
