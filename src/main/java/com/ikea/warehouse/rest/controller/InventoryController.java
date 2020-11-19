package com.ikea.warehouse.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ikea.warehouse.model.Article;
import com.ikea.warehouse.rest.mapper.ArticleMapper;
import com.ikea.warehouse.rest.model.ArticleImportRequest;
import com.ikea.warehouse.rest.model.ArticleResponse;
import com.ikea.warehouse.service.ArticleService;
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
@RequestMapping("/inventory")
public class InventoryController {
    private static final ArticleMapper articleMapper = ArticleMapper.INSTANCE;
    private ArticleService articleService;

    public InventoryController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping()
    public List<ArticleResponse> list() {
        List<Article> articles = articleService.list();

        return articleMapper.articleListToArticleResponseList(articles);
    }

    @PostMapping(name = "/import", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity importJson(@RequestParam("file") MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        ArticleImportRequest articleImportRequest;
        try {
            InputStream inJson = file.getResource().getInputStream();
            articleImportRequest = new ObjectMapper().readValue(inJson, ArticleImportRequest.class);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Couldn't read import file: " + fileName + '!');
        }

        if (articleImportRequest != null) {
            try {
                List<Article> articles = articleMapper.articleRequestListToArticleList(articleImportRequest.getInventory());
                articleService.importMultiple(articles);
            } catch (Exception ex) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
            }
        }
        return ResponseEntity.ok().build();
    }

}
