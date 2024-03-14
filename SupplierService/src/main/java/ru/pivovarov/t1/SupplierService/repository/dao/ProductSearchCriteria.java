package ru.pivovarov.t1.SupplierService.repository.dao;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductSearchCriteria {
    private String search;
    private String name;
    private String description;
    private int size = 5;
    private int page = 0;
}
