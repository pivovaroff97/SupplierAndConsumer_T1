package ru.pivovarov.t1.SupplierService.repository.dao;

import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import ru.pivovarov.t1.SupplierService.model.Category;
import ru.pivovarov.t1.SupplierService.model.Product;

public class ProductSpecification implements Specification<Product> {

    private final SpecSearchCriteria criteria;

    public ProductSpecification(SpecSearchCriteria criteria) {
        super();
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        String key = criteria.getKey();
        Object value = criteria.getValue();
        String operation = criteria.getOperation();
        if (criteria.getKey().equals("category")) {
            Join<Product, Category> categoryJoin = root.join("category");
            return builder.equal(categoryJoin.get("id"), value);
        }
        if (criteria.getOperation().equalsIgnoreCase(">")) {
            return builder.greaterThanOrEqualTo(
                    root.get(key), value.toString());
        }
        else if (criteria.getOperation().equalsIgnoreCase("<")) {
            return builder.lessThanOrEqualTo(
                    root.get(key), value.toString());
        }
        else if (criteria.getOperation().equalsIgnoreCase(":")) {
            return builder.equal(root.get(key), value);
        }
        return null;
    }
}
