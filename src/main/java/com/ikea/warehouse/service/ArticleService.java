package com.ikea.warehouse.service;

import com.ikea.warehouse.model.Article;

import java.util.List;

public interface ArticleService {
    List<Article> list();

    void importMultiple(List<Article> products);
}
