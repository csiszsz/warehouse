package com.ikea.warehouse.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ikea.warehouse.dto.ProductImportRequest;
import com.ikea.warehouse.dto.ProductResponse;
import com.ikea.warehouse.mapper.ProductMapper;
import com.ikea.warehouse.model.Product;
import com.ikea.warehouse.service.ProductService;
import lombok.extern.java.Log;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Controller
@RequestMapping("/products")
@Log
public class ProductController {
    private static final ProductMapper productMapper = ProductMapper.INSTANCE;
    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    public String list(Model model) {
        List<Product> allProducts = productService.list();
        List<ProductResponse> productResponses = productMapper.productListToProductResponseList(allProducts);
        model.addAttribute("products", productResponses);

        return "products";
    }

    @PostMapping("/import")
    public String importJson(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes, Model model) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        ProductImportRequest productImportRequest = null;
        try {
            InputStream inJson = file.getResource().getInputStream();
            productImportRequest = new ObjectMapper().readValue(inJson, ProductImportRequest.class);
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("message", "Couldn't read import file: " + fileName + '!');
        }

        try {
            List<Product> products = null;
            if (productImportRequest != null) {
                products = productMapper.productRequestListToProductList(productImportRequest.getProducts());
            }
            productService.importMultiple(products);
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
        }

        List<Product> allProducts = productService.list();
        List<ProductResponse> productResponses = productMapper.productListToProductResponseList(allProducts);
        model.addAttribute("products", productResponses);


        return "redirect:/products";
    }
}
