package com.ikea.warehouse.service.model;

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

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<ProductArticle> productArticles;
}
