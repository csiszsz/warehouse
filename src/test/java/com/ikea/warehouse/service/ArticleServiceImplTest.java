package com.ikea.warehouse.service;

import com.ikea.warehouse.dao.ArticleRepository;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class ArticleServiceImplTest {


    @Mock
    ArticleRepository articleRepository;

    @InjectMocks
    ArticleServiceImpl articleService;

    @Test
    void list() {
        Article article = new Article();
        article.setId(10L);
        article.setName("Article");
        article.setStock(30);


        when(articleRepository.findAll()).thenReturn(Collections.singletonList(article));

        List<Article> articles = articleService.list();

        assertEquals(1, articles.size());
        assertEquals("Article", articles.get(0).getName());
        assertEquals(30, articles.get(0).getStock());
    }

    @Test
    void importMultiple() {
        Article article = new Article();
        article.setId(10L);
        article.setName("Article");
        article.setStock(30);

        articleService.importMultiple(Collections.singletonList(article));
        verify(articleRepository).saveAndFlush(any());
    }
}