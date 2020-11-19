package com.ikea.warehouse.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ikea.warehouse.model.Product;
import com.ikea.warehouse.rest.mapper.ProductMapper;
import com.ikea.warehouse.rest.model.ProductImportRequest;
import com.ikea.warehouse.rest.model.ProductResponse;
import com.ikea.warehouse.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/products")
@Slf4j
public class ProductController {
    private static final ProductMapper productMapper = ProductMapper.INSTANCE;
    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    public List<ProductResponse> list() {
        List<Product> allProducts = productService.list();

        return productMapper.productListToProductResponseList(allProducts);
    }

    @PostMapping(name = "/import", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Object> importJson(@RequestParam("file") MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        ProductImportRequest productImportRequest;
        try {
            InputStream inJson = file.getResource().getInputStream();
            productImportRequest = new ObjectMapper().readValue(inJson, ProductImportRequest.class);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Couldn't read import file: " + fileName + '!');
        }

        if (productImportRequest != null) {
            try {
                List<Product> products = productMapper.productRequestListToProductList(productImportRequest.getProducts());
                productService.importMultiple(products);
            } catch (Exception ex) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
            }
        }

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/sell/{id}")
    public ResponseEntity<Object> sell(@PathVariable Long id) {
        log.info("Sell item with id: " + id);

        try {
            productService.sell(id);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }

        return ResponseEntity.ok().build();
    }
}
