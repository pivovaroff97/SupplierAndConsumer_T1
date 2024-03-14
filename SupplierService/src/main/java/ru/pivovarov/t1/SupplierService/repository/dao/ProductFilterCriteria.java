package ru.pivovarov.t1.SupplierService.repository.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ProductFilterCriteria {
    private String key;
    private String operation;
    private Object value;
}
