package ru.pivovarov.t1.SupplierService.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
public class ProductPageResponse<T> {
    private List<T> content;
    private int size;
    private long totalSize;
    private int page;
    private int totalPages;

    public ProductPageResponse(Page<T> pageable) {
        content = pageable.getContent();
        size = pageable.getSize();
        totalSize = pageable.getTotalElements();
        page = pageable.getNumber();
        totalPages = pageable.getTotalPages();
    }
}
