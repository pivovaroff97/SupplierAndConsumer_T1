package ru.pivovarov.t1.ConsumerService.service;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.pivovarov.t1.ConsumerService.exception.BadRequestException;
import ru.pivovarov.t1.ConsumerService.exception.NotFoundException;
import ru.pivovarov.t1.ConsumerService.model.Category;
import ru.pivovarov.t1.ConsumerService.model.Product;
import ru.pivovarov.t1.ConsumerService.model.ProductPageResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class RestClientServiceImpl implements RestClientService {
    private final RestTemplate restTemplate;

    @Value("${supplier-service.url}")
    private String supplierServiceUrl;
    @Autowired
    public RestClientServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    public Category addCategory(Category category) {
        ResponseEntity<Category> responseEntity = restTemplate.postForEntity(
                supplierServiceUrl.concat("/categories"), category, Category.class);
        return responseEntity.getBody();
    }

    @Override
    public List<Category> getAllCategories() {
        ResponseEntity<Category[]> responseEntity = restTemplate.getForEntity(
                supplierServiceUrl.concat("/categories"), Category[].class);
        return new ArrayList<>(List.of(responseEntity.getBody()));
    }

    @Override
    public Category getCategory(Long id) {
        ResponseEntity<Category> responseEntity = restTemplate.getForEntity(
                supplierServiceUrl.concat("/categories/" + id), Category.class);
        return responseEntity.getBody();
    }

    @Override
    public void updateCategory(Category category) {
        restTemplate.put(supplierServiceUrl.concat("/categories/{id}"), category, Map.of("id", category.getId()));
    }

    @Override
    public void deleteCategory(Long id) {
        restTemplate.delete(supplierServiceUrl.concat("/categories/" + id));
    }

    @Override
    public void printCategoryInfo(List<Category> categories) {
        for (Category c : categories) {
            System.out.println(c);
        }
    }

    @Override
    public Product addProduct(Product product) {
        ResponseEntity<Product> responseEntity = restTemplate.postForEntity(
                supplierServiceUrl.concat("/products"), product, Product.class);
        return responseEntity.getBody();
    }

    @Override
    public List<Product> getAllProducts(String params) {
        String url = supplierServiceUrl.concat("/products");
        if (params != null) {
            url = url.concat("?").concat(params);
        }
        ResponseEntity<ProductPageResponse> response = restTemplate.getForEntity(url, ProductPageResponse.class);
        List<Product> products = response.getBody().getContent();
        return products;
    }

    @Override
    public Product getProduct(Long id) {
        ResponseEntity<Product> responseEntity = restTemplate.getForEntity(
                supplierServiceUrl.concat("/products/" + id), Product.class);
        return responseEntity.getBody();
    }

    @Override
    public void printProductInfo(List<Product> products) {
        for (Product p : products) {
            System.out.println(p);
        }
    }

    @Override
    public void updateProduct(Product product) {
        restTemplate.put(supplierServiceUrl.concat("/products/{id}"), product, Map.of("id", product.getId()));
    }

    @Override
    public void deleteProduct(Long id) {
        restTemplate.delete(supplierServiceUrl.concat("/products/{id}"), Map.of("id", id));
    }

    public void checkBadRequest() {
        restTemplate.getForEntity(supplierServiceUrl.concat("/check"), Category[].class);
    }

    @PostConstruct
    public void init() {
        try {
            checkBadRequest();
        } catch (BadRequestException e) {
            System.out.println("error: " + e.getMessage());
        }
        Category cornCategory = Category.builder()
                .name("corn")
                .build();
        cornCategory = addCategory(cornCategory);
        Product saltPopCorn = Product.builder()
                .category(cornCategory)
                .price(11)
                .description("salt popcorn")
                .name("funny corn")
                .build();
        addProduct(saltPopCorn);
        Product sweetPopCorn = Product.builder()
                .category(cornCategory)
                .price(-10)
                .description("sweet popcorn")
                .name("funny corn")
                .build();
        try {
            addProduct(sweetPopCorn);
        } catch (BadRequestException e) {
            System.out.println("error: " + e.getMessage());
        }
        try {
            getCategory(1000L);
        } catch (NotFoundException e) {
            System.out.println("error: " + e.getMessage());
        }
        printCategoryInfo(getAllCategories());
        printProductInfo(getAllProducts(null));
        printProductInfo(getAllProducts("search=name:beef"));
        printProductInfo(getAllProducts("size=15"));
    }
}
