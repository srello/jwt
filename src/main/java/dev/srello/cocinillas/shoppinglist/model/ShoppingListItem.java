package dev.srello.cocinillas.shoppinglist.model;

import dev.srello.cocinillas.product.model.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "shopping_list_items")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingListItem {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column
    private Double quantity;

    @Column
    private Boolean checked;
}
