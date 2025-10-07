package dev.srello.cocinillas.recipe.rdto;

public record InstructionRSRDTO(
        Long id,
        String description,
        Double timer,
        String imageUrl
) {
}
