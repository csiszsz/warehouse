package com.ikea.warehouse.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequest {
    private Long id;
    private String name;
    private Integer price;
    @JsonProperty("contain_articles")
    private List<ContainArticleRequest> containArticles;


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ContainArticleRequest {
        @JsonProperty("art_id")
        private Long articleId;
        @JsonProperty("amount_of")
        private int amountOf;
    }
}
