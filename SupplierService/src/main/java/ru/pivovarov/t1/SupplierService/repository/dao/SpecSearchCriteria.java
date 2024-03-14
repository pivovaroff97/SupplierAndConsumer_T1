package ru.pivovarov.t1.SupplierService.repository.dao;

import lombok.Getter;

@Getter
public class SpecSearchCriteria {
    private String key;
    private Object value;
    private String operation;

    public SpecSearchCriteria(String key, Object value, String operation) {
        this.key = key;
        this.operation = operation;
        this.value = value;
    }
}
