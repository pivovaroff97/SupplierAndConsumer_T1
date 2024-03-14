package ru.pivovarov.t1.ConsumerService.service;

import ru.pivovarov.t1.ConsumerService.model.Category;
import ru.pivovarov.t1.ConsumerService.model.Product;

import java.util.List;

public interface RestClientService {
    List<Product> getAllProducts(String params);
    Product getProduct(Long id);
    Product addProduct(Product product);
    void updateProduct(Product product);
    void deleteProduct(Long id);
    void printProductInfo(List<Product> products);
    List<Category> getAllCategories();
    Category getCategory(Long id);
    Category addCategory(Category category);
    void updateCategory(Category category);
    void deleteCategory(Long id);
    void printCategoryInfo(List<Category> categories);
}
