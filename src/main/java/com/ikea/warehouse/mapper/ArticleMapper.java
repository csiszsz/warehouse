package com.ikea.warehouse.mapper;

import com.ikea.warehouse.dto.ArticleRequest;
import com.ikea.warehouse.dto.ArticleResponse;
import com.ikea.warehouse.model.Article;
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
