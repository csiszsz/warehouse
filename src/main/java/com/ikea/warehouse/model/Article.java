package com.ikea.warehouse.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity(name = "articles")
public class Article {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private int stock;
}
