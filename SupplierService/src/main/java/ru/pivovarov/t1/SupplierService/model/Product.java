package ru.pivovarov.t1.SupplierService.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "name is required field")
    @Column
    private String name;

    @NotBlank(message = "description is required field")
    @Column
    private String description;

    @Min(value = 1, message = "price must be greater then 0")
    @Column
    private int price;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    @JsonIgnoreProperties("products")
    private Category category;
}
