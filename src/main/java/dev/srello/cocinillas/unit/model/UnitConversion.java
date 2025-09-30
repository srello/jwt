package dev.srello.cocinillas.unit.model;

import dev.srello.cocinillas.product.model.Product;
import dev.srello.cocinillas.unit.enums.Unit;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "unit_conversions")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UnitConversion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @Column(nullable = false)
    private Unit targetUnit;

    @Column(nullable = false)
    private Double conversionFactor;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}
