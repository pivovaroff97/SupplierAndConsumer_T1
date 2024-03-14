package ru.pivovarov.t1.ConsumerService.model;

import lombok.*;


@Getter
@Setter
@Builder
@ToString
public class Product {

    private Long id;
    private String name;
    private String description;
    private int price;
    private Category category;
}
