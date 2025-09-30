package dev.srello.cocinillas.supermarket.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "supermarket_products")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SupermarketProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;
}
