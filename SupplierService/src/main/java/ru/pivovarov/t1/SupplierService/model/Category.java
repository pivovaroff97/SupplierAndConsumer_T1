package ru.pivovarov.t1.SupplierService.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Name is required field")
    @Column
    private String name;

    @OneToMany(mappedBy = "category")
    private List<Product> products = new ArrayList<>();
}
