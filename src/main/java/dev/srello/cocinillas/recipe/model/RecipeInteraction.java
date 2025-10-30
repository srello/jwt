package dev.srello.cocinillas.recipe.model;

import dev.srello.cocinillas.recipe.enums.RecipeInteractionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "recipe_interactions")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecipeInteraction {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @Column
    private Long userId;

    @Column
    private Long recipeId;

    @Column
    private RecipeInteractionType type;
}
