package com.ikea.warehouse.rest.controller;

import com.ikea.warehouse.model.Article;
import com.ikea.warehouse.model.Product;
import com.ikea.warehouse.model.ProductArticle;
import com.ikea.warehouse.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {
    @MockBean
    ProductService productService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void list() throws Exception {
        Product product = new Product();
        product.setId(1L);
        product.setName("Product");
        product.setPrice(100);

        Article article = new Article();
        article.setId(10L);
        article.setName("Article");
        article.setStock(3);

        ProductArticle productArticle = new ProductArticle();
        productArticle.setProduct(product);
        productArticle.setArticle(article);
        productArticle.setAmountOf(50);
        product.setProductArticles(Collections.singletonList(productArticle));

        given(productService.list()).willReturn(Collections.singletonList(product));

        mockMvc.perform(get("/products")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Product")))
                .andExpect(jsonPath("$[0].price", is(100)))
                .andExpect(jsonPath("$[0].containArticles", hasSize(1)))
                .andExpect(jsonPath("$[0].containArticles[0].amountOf", is(50)))
                .andExpect(jsonPath("$[0].containArticles[0].article.id", is(10)))
                .andExpect(jsonPath("$[0].containArticles[0].article.name", is("Article")))
                .andExpect(jsonPath("$[0].containArticles[0].article.stock", is(3)));

    }

    @Test
    void importJson() throws Exception {
//        MockMultipartFile multipartFile = new MockMultipartFile("file", "test.txt", "text/plain", "Invalid Product".getBytes());
//
//        mockMvc.perform(multipart("/products/import").file(multipartFile)).andExpect(status().isBadRequest());
    }

    @Test
    void sell() {

    }
}