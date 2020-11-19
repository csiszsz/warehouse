package com.ikea.warehouse.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArticleRequest {
    @JsonProperty("art_id")
    private Long id;

    private String name;

    private Integer stock;
}
