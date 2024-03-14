package ru.pivovarov.t1.SupplierService.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.pivovarov.t1.SupplierService.model.Product;
import ru.pivovarov.t1.SupplierService.model.ProductPageResponse;
import ru.pivovarov.t1.SupplierService.repository.dao.ProductSearchCriteria;
import ru.pivovarov.t1.SupplierService.repository.dao.ProductSpecificationsBuilder;
import ru.pivovarov.t1.SupplierService.service.ProductService;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/products")
public class RestApiProductController {

    private final ProductService productService;

    @Autowired
    public RestApiProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<ProductPageResponse<Product>> getProducts(ProductSearchCriteria productSearchCriteria) {
        ProductSpecificationsBuilder builder = new ProductSpecificationsBuilder();
        builder.specSearchCriteriaBuild(productSearchCriteria);
        Specification<Product> spec = builder.build();
        Pageable pageable = PageRequest.of(productSearchCriteria.getPage(), productSearchCriteria.getSize());
        Page<Product> page = productService.getProductsWithFilter(spec, pageable);
        return ResponseEntity
                .ok()
                .body(new ProductPageResponse<>(page));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return ResponseEntity
                .ok()
                .body(productService.getProductById(id));
    }

    @PostMapping
    public ResponseEntity<Product> saveProduct(@Valid @RequestBody Product product) {
        product = productService.saveProduct(product);
        return ResponseEntity
                .created(URI.create("/products/" + product.getId()))
                .body(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> upsertProduct(@Valid @RequestBody Product product, @PathVariable Long id) {
        if (productService.existById(id)) {
            return ResponseEntity
                    .ok()
                    .body(productService.upsertProductById(product, id));
        } else {
            return saveProduct(product);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProductById(id);
    }
}
