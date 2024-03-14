package ru.pivovarov.t1.SupplierService.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.pivovarov.t1.SupplierService.model.Category;
import ru.pivovarov.t1.SupplierService.service.CategoryService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class RestApiCategoryController {

    private final CategoryService categoryService;

    @Autowired
    public RestApiCategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<Category>> getCategories() {
        return ResponseEntity
                .ok()
                .body(categoryService.getCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        return ResponseEntity
                .ok()
                .body(categoryService.getCategoryById(id));
    }

    @PostMapping
    public ResponseEntity<Category> saveCategory(@Valid @RequestBody Category category) {
        category = categoryService.saveCategory(category);
        return ResponseEntity
                .created(URI.create("/categories/" + category.getId()))
                .body(category);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> upsertCategory(@Valid @RequestBody Category category, @PathVariable Long id) {
        if (categoryService.existById(id)) {
            return ResponseEntity
                    .ok()
                    .body(categoryService.upsertCategoryById(category, id));
        } else {
            return saveCategory(category);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteCategoryById(@PathVariable Long id) {
        categoryService.deleteCategoryById(id);
    }

}
