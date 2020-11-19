package com.ikea.warehouse.dao;

import com.ikea.warehouse.service.model.ProductArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductArticleRepository extends JpaRepository<ProductArticle, Long> {

}

