package ru.pivovarov.t1.SupplierService.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import ru.pivovarov.t1.SupplierService.model.Product;

public interface ProductService {
    Page<Product> getProductsWithFilter(Specification<Product> spec, Pageable pageable);
    Product getProductById(Long id);
    Product saveProduct(Product product);
    Product upsertProductById(Product product, Long id);
    void deleteProductById(Long id);
    Boolean existById(Long id);
}
