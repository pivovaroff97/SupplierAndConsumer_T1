package ru.pivovarov.t1.SupplierService.repository.dao;

import org.springframework.data.jpa.domain.Specification;
import ru.pivovarov.t1.SupplierService.model.Product;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProductSpecificationsBuilder {
    private final List<SpecSearchCriteria> params;

    private final static Set<String> operations = new HashSet<>(){{
        add(":");
        add(">");
        add("<");
    }};
    private ProductSearchCriteria productSearchCriteria;

    public ProductSpecificationsBuilder() {
        params = new ArrayList<>();
    }

    public void specSearchCriteriaBuild(ProductSearchCriteria productSearchCriteria) {
        Pattern pattern = Pattern.compile("(\\w+?)([:<>])(\\w+?),");
        Matcher matcher = pattern.matcher(productSearchCriteria + ",");
        while (matcher.find()) {
            with(matcher.group(1), matcher.group(2), matcher.group(3));
        }
        if (productSearchCriteria.getName() != null) {
            with("name", ":", productSearchCriteria.getName());
        }
        if (productSearchCriteria.getDescription() != null) {
            with("description", ":", productSearchCriteria.getDescription());
        }
    }

    public final ProductSpecificationsBuilder with(String key, String operation, Object value) {
        if (operations.contains(operation)) {
            params.add(new SpecSearchCriteria(key, value, operation));
        }
        return this;
    }

    public Specification<Product> build() {
        if (params.size() == 0)
            return null;
        Specification<Product> result = new ProductSpecification(params.get(0));
        for (int i = 1; i < params.size(); i++) {
            result = Specification.where(result).and(new ProductSpecification(params.get(i)));
        }

        return result;
    }
}
