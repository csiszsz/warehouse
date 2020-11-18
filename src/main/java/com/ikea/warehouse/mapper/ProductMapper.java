package com.ikea.warehouse.mapper;

import com.ikea.warehouse.dto.ArticleResponse;
import com.ikea.warehouse.dto.ProductRequest;
import com.ikea.warehouse.dto.ProductResponse;
import com.ikea.warehouse.model.Article;
import com.ikea.warehouse.model.Product;
import com.ikea.warehouse.model.ProductArticle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "productArticles", target = "containArticles"),
    })
    ProductResponse productToProductResponse(Product product);

    ArticleResponse articleToArticleResponse(Article article);

    List<ProductResponse> productListToProductResponseList(List<Product> product);


    List<Product> productRequestListToProductList(List<ProductRequest> productRequestList);

    @Mappings({
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "containArticles", target = "productArticles"),
    })
    Product productRequestToProduct(ProductRequest productRequest);

    @Mappings({
            @Mapping(source = "artId", target = "article.id"),
    })
    ProductArticle containArticlesRequestToProductArticles(ProductRequest.ContainArticleRequest containArticle);
}
