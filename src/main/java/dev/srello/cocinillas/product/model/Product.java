package dev.srello.cocinillas.product.model;

import dev.srello.cocinillas.allergen.model.Allergen;
import dev.srello.cocinillas.product.enums.ProductCategory;
import dev.srello.cocinillas.shared.enums.Visibility;
import dev.srello.cocinillas.tags.model.Tag;
import dev.srello.cocinillas.unit.enums.Unit;
import dev.srello.cocinillas.unit.model.UnitConversion;
import dev.srello.cocinillas.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.EAGER;

@Entity
@Table(name = "products")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private ProductCategory productCategory;

    @Column(nullable = false)
    private Unit baseUnit;

    @OneToMany(mappedBy = "product", fetch = EAGER, cascade = ALL)
    private List<UnitConversion> unitConversions;

    @Column
    private Double calories;

    @Column
    private Double protein;

    @Column
    private Double carbohydrates;

    @Column
    private Double fat;

    @Column
    @ManyToMany(fetch = EAGER)
    @JoinTable(
            name = "product_allergens",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "allergen_id")
    )
    private List<Allergen> allergens;

    @ManyToMany(cascade = {ALL}, fetch = EAGER)
    private List<Tag> tags;

    @Column
    private Visibility visibility;

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;
}
