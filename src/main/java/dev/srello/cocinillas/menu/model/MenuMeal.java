package dev.srello.cocinillas.menu.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "menu_meals")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuMeal {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ElementCollection(fetch = EAGER)
    @CollectionTable(
            name = "menu_meal_recipes",
            joinColumns = @JoinColumn(name = "menu_meal_id")
    )
    @Column(name = "recipe_id")
    private List<Long> recipeIds;

    @Column(nullable = false)
    private LocalTime hour;

    @Column(nullable = false)
    private Integer dayIndex;
}
