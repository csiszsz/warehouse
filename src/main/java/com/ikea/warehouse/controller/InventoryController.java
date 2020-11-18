package com.ikea.warehouse.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ikea.warehouse.dto.ArticleImportRequest;
import com.ikea.warehouse.dto.ArticleResponse;
import com.ikea.warehouse.mapper.ArticleMapper;
import com.ikea.warehouse.model.Article;
import com.ikea.warehouse.service.ArticleService;
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
@RequestMapping("/inventory")
public class InventoryController {
    private static final ArticleMapper articleMapper = ArticleMapper.INSTANCE;
    private ArticleService articleService;

    public InventoryController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping()
    public String list(Model model) {

        List<Article> articles = articleService.list();
        List<ArticleResponse> articleResponses = articleMapper.articleListToArticleResponseList(articles);
        model.addAttribute("articles", articleResponses);

        return "inventory";
    }


    @PostMapping("/import")
    public String importJson(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes, Model model) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        ArticleImportRequest articleImportRequest = null;
        try {
            InputStream inJson = file.getResource().getInputStream();
            articleImportRequest = new ObjectMapper().readValue(inJson, ArticleImportRequest.class);
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("message", "Couldn't read import file: " + fileName + '!');
        }

        try {
            List<Article> articles = null;
            if (articleImportRequest != null) {
                articles = articleMapper.articleRequestListToArticleList(articleImportRequest.getInventory());
            }
            articleService.importMultiple(articles);
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
        }

        List<Article> articles = articleService.list();
        List<ArticleResponse> articleResponses = articleMapper.articleListToArticleResponseList(articles);
        model.addAttribute("articles", articleResponses);


        return "redirect:/inventory";
    }

}
