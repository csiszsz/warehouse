package com.ikea.warehouse.rest.mapper;

import com.ikea.warehouse.rest.model.ArticleRequest;
import com.ikea.warehouse.rest.model.ArticleResponse;
import com.ikea.warehouse.service.model.Article;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ArticleMapper {
    ArticleMapper INSTANCE = Mappers.getMapper(ArticleMapper.class);

    Article articleRequestToArticle(ArticleRequest articleRequest);

    List<Article> articleRequestListToArticleList(List<ArticleRequest> articleRequestList);

    List<ArticleResponse> articleListToArticleResponseList(List<Article> articleList);
}
