package dev.srello.cocinillas.tags.controller;

import dev.srello.cocinillas.shared.pagination.dto.PaginationIDTO;
import dev.srello.cocinillas.shared.pagination.rdto.PaginationRQRDTO;
import dev.srello.cocinillas.shared.pagination.transformer.PaginationTransformer;
import dev.srello.cocinillas.tags.controller.transformer.TagControllerTransformer;
import dev.srello.cocinillas.tags.dto.GetTagsIDTO;
import dev.srello.cocinillas.tags.dto.TagODTO;
import dev.srello.cocinillas.tags.rdto.GetTagsRQRDTO;
import dev.srello.cocinillas.tags.rdto.TagRSRDTO;
import dev.srello.cocinillas.tags.service.TagService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.http.ResponseEntity.ok;

@Controller
@RequiredArgsConstructor
@RequestMapping("/tags")
public class TagController {

    private final TagControllerTransformer transformer;
    private final PaginationTransformer paginationTransformer;
    private final TagService service;

    @GetMapping
    public ResponseEntity<Page<TagRSRDTO>> getTagsPaginated(@Valid GetTagsRQRDTO getTagsRQRDTO, @Valid PaginationRQRDTO paginationRQRDTO) {
        GetTagsIDTO getTagsIDTO = transformer.toGetTagsIDTO(getTagsRQRDTO);
        PaginationIDTO paginationIDTO = paginationTransformer.toPaginationIDTO(paginationRQRDTO);
        Page<TagODTO> tagsODTO = service.getTagsPaginated(getTagsIDTO, paginationIDTO);
        Page<TagRSRDTO> tagsRSRDTO = transformer.toTagsRSRDTO(tagsODTO);
        return ok().body(tagsRSRDTO);
    }
}
