package ru.pivovarov.t1.SupplierService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.pivovarov.t1.SupplierService.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
