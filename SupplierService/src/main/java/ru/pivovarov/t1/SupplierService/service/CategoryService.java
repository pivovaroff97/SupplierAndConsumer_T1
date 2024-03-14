package ru.pivovarov.t1.SupplierService.service;

import ru.pivovarov.t1.SupplierService.model.Category;

import java.util.List;

public interface CategoryService {

    List<Category> getCategories();
    Category getCategoryById(Long id);
    Category saveCategory(Category category);
    Category upsertCategoryById(Category category, Long id);
    void deleteCategoryById(Long id);
    Boolean existById(Long id);
}
