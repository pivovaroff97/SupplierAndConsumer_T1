package ru.pivovarov.t1.SupplierService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pivovarov.t1.SupplierService.model.Category;
import ru.pivovarov.t1.SupplierService.repository.CategoryRepository;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow();
    }

    @Override
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category upsertCategoryById(Category category, Long id) {
        return categoryRepository.findById(id)
                .map(c -> {
                    c.setName(c.getName());
                    return categoryRepository.save(c);
                })
                .orElseGet(() -> {
                    return categoryRepository.save(category);
                });
    }

    @Override
    public void deleteCategoryById(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public Boolean existById(Long id) {
        return categoryRepository.existsById(id);
    }
}
