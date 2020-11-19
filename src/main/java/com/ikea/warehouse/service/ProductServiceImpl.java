package com.ikea.warehouse.service;

import com.ikea.warehouse.dao.ArticleRepository;
import com.ikea.warehouse.dao.ProductArticleRepository;
import com.ikea.warehouse.dao.ProductRepository;
import com.ikea.warehouse.exception.ArticleNotFoundException;
import com.ikea.warehouse.exception.ProductImportException;
import com.ikea.warehouse.exception.ProductNotFoundException;
import com.ikea.warehouse.model.Article;
import com.ikea.warehouse.model.Product;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;
    private ProductArticleRepository productArticleRepository;
    private ArticleRepository articleRepository;

    public ProductServiceImpl(ProductRepository productRepository, ProductArticleRepository productArticleRepository, ArticleRepository articleRepository) {
        this.productRepository = productRepository;
        this.productArticleRepository = productArticleRepository;
        this.articleRepository = articleRepository;
    }

    @Override
    public List<Product> list() {
        List<Product> availableProducts = productRepository.findAll();

        availableProducts.forEach(product -> {
            int quantity = product.getProductArticles().stream()
                    .mapToInt(productArticle -> {
                        int amountOf = productArticle.getAmountOf();
                        int stock = productArticle.getArticle().getStock();
                        return stock / amountOf;
                    })
                    .min().orElse(0);
            product.setQuantity(quantity);
        });

        return availableProducts;
    }

    @Override
    public void importMultiple(List<Product> products) {
        products.forEach(product -> {
            try {
                productRepository.saveAndFlush(product);
                product.getProductArticles().forEach(productArticle -> {
                    productArticle.setProduct(product);
                    productArticleRepository.saveAndFlush(productArticle);
                });
            } catch (DataIntegrityViolationException ex) {
                throw new ProductImportException(product.getName());
            }
        });
    }

    @Override
    public void sell(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        Product product = optionalProduct.orElseThrow(() -> new ProductNotFoundException(id));

        product.getProductArticles().forEach(productArticle -> {
            Long articleId = productArticle.getArticle().getId();
            Optional<Article> articleOptional = articleRepository.findById(articleId);
            Article article = articleOptional.orElseThrow(() -> new ArticleNotFoundException(articleId));

            article.setStock(article.getStock() - productArticle.getAmountOf());
            articleRepository.saveAndFlush(article);
        });
    }
}
