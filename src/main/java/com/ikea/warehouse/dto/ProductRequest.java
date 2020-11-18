package com.ikea.warehouse.dto;

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
    @JsonProperty("contain_articles")
    private List<ContainArticleRequest> containArticles;


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ContainArticleRequest {
        @JsonProperty("art_id")
        private Long artId;
        @JsonProperty("amount_of")
        private int amountOf;
    }
}
