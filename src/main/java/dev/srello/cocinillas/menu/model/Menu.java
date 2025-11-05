package dev.srello.cocinillas.menu.model;

import dev.srello.cocinillas.tags.model.Tag;
import dev.srello.cocinillas.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "menus")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Menu {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(cascade = ALL, fetch = EAGER)
    private List<MenuMeal> menuMeals;

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @ManyToMany(fetch = EAGER)
    private List<Tag> tags;
}
