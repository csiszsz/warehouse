package com.ikea.warehouse.service;

import com.ikea.warehouse.model.Product;

import java.util.List;

public interface ProductService {
    List<Product> list();

    void importMultiple(List<Product> products);
}