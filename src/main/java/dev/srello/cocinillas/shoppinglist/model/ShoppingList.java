package dev.srello.cocinillas.shoppinglist.model;

import dev.srello.cocinillas.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.GenerationType.IDENTITY;
import static java.lang.Boolean.FALSE;

@Entity
@Table(name = "shopping_lists")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingList {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @OneToMany(cascade = ALL, fetch = EAGER)
    private List<ShoppingListItem> items;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;

    @Column(nullable = false)
    private Boolean completed = FALSE;

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;
}
