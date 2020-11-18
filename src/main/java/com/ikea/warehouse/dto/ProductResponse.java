package com.ikea.warehouse.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponse {
    private Long id;
    private String name;
    private List<ContainArticleResponse> containArticles;


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ContainArticleResponse {
        private ArticleResponse article;
        private Integer amountOf;
    }
}
