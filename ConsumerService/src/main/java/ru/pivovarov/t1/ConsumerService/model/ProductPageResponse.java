package ru.pivovarov.t1.ConsumerService.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ProductPageResponse {
    private List<Product> content;
    private int size;
    private long totalSize;
    private int page;
    private int totalPages;
}
