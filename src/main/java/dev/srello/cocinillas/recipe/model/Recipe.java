package dev.srello.cocinillas.recipe.model;

import dev.srello.cocinillas.recipe.enums.RecipeVisibility;
import dev.srello.cocinillas.tags.model.Tag;
import dev.srello.cocinillas.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "recipes")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 3000)
    private String description;

    @OneToMany(fetch = EAGER, cascade = ALL, orphanRemoval = true)
    private List<Instruction> instructions;

    @ManyToMany(fetch = EAGER)
    private List<Ingredient> ingredients;

    @Column
    private RecipeVisibility visibility;

    @Column(nullable = false)
    private Integer totalDuration;

    @ManyToMany(cascade = {ALL}, fetch = EAGER)
    private List<Tag> tags;

    @ElementCollection(fetch = EAGER)
    @CollectionTable(
            name = "recipe_images",
            joinColumns = @JoinColumn(name = "recipe_id")
    )
    @Column(name = "key")
    private List<String> imageKeys = new ArrayList<>();

    @Column(nullable = false)
    private LocalDateTime creationDate;

    @Column
    private Long likes = 0L;

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

}
