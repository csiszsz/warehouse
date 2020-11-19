package com.ikea.warehouse.rest.controller;

import com.ikea.warehouse.model.Article;
import com.ikea.warehouse.service.ArticleService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class InventoryControllerTest {
    @MockBean
    ArticleService articleService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void list() throws Exception {

        Article article = new Article();
        article.setId(10L);
        article.setName("Article");
        article.setStock(3);

        given(articleService.list()).willReturn(Collections.singletonList(article));

        mockMvc.perform(get("/inventory")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(10)))
                .andExpect(jsonPath("$[0].name", is("Article")))
                .andExpect(jsonPath("$[0].stock", is(3)));
    }

    @Test
    void importJson() throws Exception {
//        MockMultipartFile multipartFile = new MockMultipartFile("file", "test.txt", "text/plain", "Invalid Article".getBytes());
//
//        mockMvc.perform(multipart("/inventory/import").file(multipartFile)).andExpect(status().isBadRequest());
    }
}