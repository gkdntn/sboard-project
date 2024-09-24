package com.sboard.service;

import com.querydsl.core.Tuple;
import com.sboard.dto.PageRequestDTO;
import com.sboard.entity.Article;
import com.sboard.repository.ArticleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ArticleServiceTest {

    @Autowired
    private ArticleRepository articleRepository;

    @Test
    void selectArticleAll() {

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().build();
        Pageable pageable = pageRequestDTO.getPageable("no");


        Page<Tuple> pageArticle = articleRepository.selectArticleAllForList(pageRequestDTO, pageable);

        System.out.println(pageArticle.getContent());
    }
}