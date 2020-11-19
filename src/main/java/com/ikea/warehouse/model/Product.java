package com.ikea.warehouse.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity(name = "product")
public class Product {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private Integer price;

    @Transient
    private Integer quantity;

    @OneToMany(mappedBy = "product")
    private List<ProductArticle> productArticles;
}
