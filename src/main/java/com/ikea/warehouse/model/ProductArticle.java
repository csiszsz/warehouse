package com.ikea.warehouse.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
public class ProductArticle implements Serializable {

    @EmbeddedId
    ProductArticleKey id = new ProductArticleKey();

    @ManyToOne(fetch = FetchType.LAZY   )
    @MapsId("productId")
    private Product product;

    @ManyToOne
    @MapsId("articleId")
    private Article article;

    private int amountOf;
}
