package com.ikea.warehouse.service;

import com.ikea.warehouse.dao.ProductArticleRepository;
import com.ikea.warehouse.dao.ProductRepository;
import com.ikea.warehouse.exception.ProductImportException;
import com.ikea.warehouse.service.model.Product;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;
    private ProductArticleRepository productArticleRepository;

    public ProductServiceImpl(ProductRepository productRepository, ProductArticleRepository productArticleRepository) {
        this.productRepository = productRepository;
        this.productArticleRepository = productArticleRepository;
    }

    @Override
    public List<Product> list() {
        return productRepository.findAll();
    }

    @Override
    public void importMultiple(List<Product> products) {

        products.forEach(product -> {
            try {
                productRepository.save(product);
                product.getProductArticles().forEach(productArticle -> {
                    productArticle.setProduct(product);
                    productArticleRepository.save(productArticle);
                });
            } catch (DataIntegrityViolationException ex) {
                throw new ProductImportException(product.getName());
            }
        });

    }
}
