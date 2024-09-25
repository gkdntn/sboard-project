package com.sboard.service;

import com.querydsl.core.Tuple;
import com.sboard.dto.ArticleDTO;
import com.sboard.dto.PageRequestDTO;
import com.sboard.dto.PageResponseDTO;
import com.sboard.dto.UserDTO;
import com.sboard.entity.Article;
import com.sboard.entity.User;
import com.sboard.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Log4j2
@RequiredArgsConstructor
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ModelMapper modelMapper;

    public int insertArticle(ArticleDTO articleDTO) {


        // ModelMapper를 이용해서 DTO를 Entity로 변환함
        Article article = modelMapper.map(articleDTO, Article.class);
        log.info(article);
        Article savedArticle = articleRepository.save(article);

        return savedArticle.getNo();
    }

    public ArticleDTO selectArticle(int no){

        Optional<Article> optArticle = articleRepository.findById(no);

        if (optArticle.isPresent()) {
            Article article = optArticle.get();
            log.info("articleService~~~~~~~" + article);

        return modelMapper.map(article, ArticleDTO.class);
        }

        return null;
    }

        

    public PageResponseDTO selectArticleAll(PageRequestDTO pageRequestDTO){

        Pageable pageable = pageRequestDTO.getPageable("no");

        Page<Tuple> pageArticle = null;

        // 엔티티 조회
        if(pageRequestDTO.getKeyword() == null) {
            pageArticle = articleRepository.selectArticleAllForList(pageRequestDTO, pageable);
        }else {
            pageArticle = articleRepository.selectArticleForSearch(pageRequestDTO, pageable);
        }
        // 엔티티 리스트를 dto 리스트로 변환
        List<ArticleDTO> articleList = pageArticle.getContent().stream().map(tuple ->{

                Article article = tuple.get(0, Article.class);
                String nick = tuple.get(1, String.class);
                article.setNick(nick);

                return modelMapper.map(article, ArticleDTO.class);

                }).toList();

        int total = (int) pageArticle.getTotalElements();

        return PageResponseDTO.builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(articleList)
                .total(total)
                .build();
    }

    public void updateArticle(ArticleDTO articleDTO) {

    }

    public void deleteArticle(int no){

          articleRepository.deleteById(no);

    }

}
