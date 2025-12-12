package dev.srello.cocinillas.shoppinglist.model;

import dev.srello.cocinillas.product.model.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "shopping_list_recurrent_items")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingListRecurrentItem {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private Integer daysToNextPurchase;

    @Column(nullable = false)
    private LocalDateTime lastPurchaseDate;
}
