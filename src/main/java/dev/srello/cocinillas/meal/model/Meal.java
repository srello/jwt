package dev.srello.cocinillas.meal.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "meals")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Meal {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long userId;

    @ElementCollection(fetch = EAGER)
    @CollectionTable(
            name = "meal_recipes",
            joinColumns = @JoinColumn(name = "meal_id")
    )
    @Column(name = "recipe_id")
    private List<Long> recipeIds;

    @Column(nullable = false)
    private LocalDateTime dateTime;
}
