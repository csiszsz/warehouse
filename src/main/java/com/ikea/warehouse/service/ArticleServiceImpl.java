package com.ikea.warehouse.service;

import com.ikea.warehouse.dao.ArticleRepository;
import com.ikea.warehouse.model.Article;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {

    private ArticleRepository articleRepository;

    public ArticleServiceImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public List<Article> list() {
        return articleRepository.findAll();
    }

    @Override
    public void importMultiple(List<Article> articles) {
        articles.forEach(article -> articleRepository.saveAndFlush(article));
    }
}
