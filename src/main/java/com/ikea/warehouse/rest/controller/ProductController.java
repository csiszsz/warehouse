package com.ikea.warehouse.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ikea.warehouse.rest.mapper.ProductMapper;
import com.ikea.warehouse.rest.model.ProductImportRequest;
import com.ikea.warehouse.rest.model.ProductResponse;
import com.ikea.warehouse.service.ProductService;
import com.ikea.warehouse.service.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

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

    @RequestMapping(
            method = RequestMethod.POST,
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE},
            name = "/import"
    )
    public List<ProductResponse> importJson(@RequestParam("file") MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        ProductImportRequest productImportRequest;
        try {
            InputStream inJson = file.getResource().getInputStream();
            productImportRequest = new ObjectMapper().readValue(inJson, ProductImportRequest.class);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Couldn't read import file: " + fileName + '!');
        }

        if (productImportRequest != null) {
            try {
                List<Product> products = productMapper.productRequestListToProductList(productImportRequest.getProducts());
                productService.importMultiple(products);
            } catch (Exception ex) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
            }
        }

        List<Product> allProducts = productService.list();

        return productMapper.productListToProductResponseList(allProducts);
    }

    @PostMapping("/sell/{id}")
    public List<ProductResponse> sell(@PathVariable String id) {
        log.info("Sell item with id: " + id);

        List<Product> allProducts = productService.list();

        return productMapper.productListToProductResponseList(allProducts);
    }
}
