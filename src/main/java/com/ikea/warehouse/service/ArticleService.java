package com.ikea.warehouse.service;

import com.ikea.warehouse.service.model.Article;

import java.util.List;

public interface ArticleService {
    List<Article> list();

    void importMultiple(List<Article> products);
}
