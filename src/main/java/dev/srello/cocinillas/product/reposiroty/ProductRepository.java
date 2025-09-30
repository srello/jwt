package dev.srello.cocinillas.product.reposiroty;

import dev.srello.cocinillas.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
