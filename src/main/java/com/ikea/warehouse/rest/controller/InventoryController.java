package com.ikea.warehouse.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ikea.warehouse.rest.mapper.ArticleMapper;
import com.ikea.warehouse.rest.model.ArticleImportRequest;
import com.ikea.warehouse.rest.model.ArticleResponse;
import com.ikea.warehouse.service.ArticleService;
import com.ikea.warehouse.service.model.Article;
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


    @RequestMapping(
            method = RequestMethod.POST,
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE},
            name = "/import"
    )
    public List<ArticleResponse> importJson(@RequestParam("file") MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        ArticleImportRequest articleImportRequest;
        try {
            InputStream inJson = file.getResource().getInputStream();
            articleImportRequest = new ObjectMapper().readValue(inJson, ArticleImportRequest.class);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Couldn't read import file: " + fileName + '!');
        }

        if (articleImportRequest != null) {
            try {
                List<Article> articles = articleMapper.articleRequestListToArticleList(articleImportRequest.getInventory());
                articleService.importMultiple(articles);
            } catch (Exception ex) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
            }
        }

        List<Article> articles = articleService.list();
        return articleMapper.articleListToArticleResponseList(articles);
    }

}
