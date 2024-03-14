package ru.pivovarov.t1.ConsumerService.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
public class Category {

    private Long id;
    private String name;
    private List<Product> products;
}
