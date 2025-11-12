package dev.srello.cocinillas.menu.model;

import dev.srello.cocinillas.menu.enums.MenuInteractionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "menu_interactions")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuInteraction {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @Column
    private Long userId;

    @Column
    private Long menuId;

    @Column
    private MenuInteractionType type;
}
