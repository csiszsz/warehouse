package com.ikea.warehouse.service;

import com.ikea.warehouse.dao.ArticleRepository;
import com.ikea.warehouse.dao.ProductArticleRepository;
import com.ikea.warehouse.dao.ProductRepository;
import com.ikea.warehouse.model.Article;
import com.ikea.warehouse.model.Product;
import com.ikea.warehouse.model.ProductArticle;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class ProductServiceImplTest {


    @Mock
    ProductRepository productRepository;

    @Mock
    ArticleRepository articleRepository;

    @Mock
    ProductArticleRepository productArticleRepository;

    @InjectMocks
    ProductServiceImpl productService;

    @Test
    void list() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Product");
        product.setPrice(100);

        Article article = new Article();
        article.setId(10L);
        article.setName("Article");
        article.setStock(30);

        ProductArticle productArticle = new ProductArticle();
        productArticle.setProduct(product);
        productArticle.setArticle(article);
        productArticle.setAmountOf(5);
        product.setProductArticles(Collections.singletonList(productArticle));

        when(productRepository.findAll()).thenReturn(Collections.singletonList(product));

        List<Product> products = productService.list();

        assertEquals(1, products.size());
        assertEquals("Product", products.get(0).getName());
        assertEquals("Article", products.get(0).getProductArticles().get(0).getArticle().getName());
        assertEquals(30, products.get(0).getProductArticles().get(0).getArticle().getStock());
        assertEquals(5, products.get(0).getProductArticles().get(0).getAmountOf());
        assertEquals(6, products.get(0).getQuantity());
    }

    @Test
    void importMultiple() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Product");
        product.setPrice(100);

        Article article = new Article();
        article.setId(10L);
        article.setName("Article");
        article.setStock(30);

        ProductArticle productArticle = new ProductArticle();
        productArticle.setProduct(product);
        productArticle.setArticle(article);
        productArticle.setAmountOf(5);
        product.setProductArticles(Collections.singletonList(productArticle));

        when(productRepository.findById(1L)).thenReturn(java.util.Optional.of(product));

        productService.importMultiple(Collections.singletonList(product));

        verify(productRepository).saveAndFlush(any());
        verify(productArticleRepository).saveAndFlush(any());
    }

    @Test
    void sell() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Product");
        product.setPrice(100);

        Article article = new Article();
        article.setId(10L);
        article.setName("Article");
        article.setStock(30);

        ProductArticle productArticle = new ProductArticle();
        productArticle.setProduct(product);
        productArticle.setArticle(article);
        productArticle.setAmountOf(5);
        product.setProductArticles(Collections.singletonList(productArticle));

        when(productRepository.findById(1L)).thenReturn(java.util.Optional.of(product));
        when(articleRepository.findById(10L)).thenReturn(java.util.Optional.of(article));

        productService.sell(1L);

        verify(articleRepository).saveAndFlush(any());
    }
}