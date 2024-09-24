package com.sboard.controller;

import com.sboard.config.AppInfo;
import com.sboard.dto.ArticleDTO;
import com.sboard.dto.FileDTO;
import com.sboard.dto.PageRequestDTO;
import com.sboard.dto.PageResponseDTO;
import com.sboard.service.ArticleService;
import com.sboard.service.FileService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Controller
public class ArticleController {

    private final ArticleService articleService;
    private final FileService fileService;

    @GetMapping("/article/list")
    public String list(Model model, PageRequestDTO pageRequestDTO) {

        // 일반 글 목록 조회
        PageResponseDTO pageResponseDTO = articleService.selectArticleAll(pageRequestDTO);
        model.addAttribute(pageResponseDTO);

        return "/article/list";
    }

    @GetMapping("/article/modify")
    public String modify() {
        return "/article/modify";
    }

    @GetMapping("/article/view")
    public String view() {
        return "/article/view";
    }

    @GetMapping("/article/write")
    public String write() {

        return "/article/write";
    }

    @PostMapping("/article/write")
    public String write(ArticleDTO articleDTO, HttpServletRequest req) {
        String regip = req.getRemoteAddr();
        articleDTO.setRegip(regip);

        log.info(articleDTO);

        // 글 저장
        int ano = articleService.insertArticle(articleDTO);

        // 파일 업로드
        List<FileDTO> uploadedFiles = fileService.uploadFile(articleDTO);

        // 파일 저장
        for(FileDTO fileDTO : uploadedFiles) {
            fileDTO.setAno(ano);
            fileService.insertFile(fileDTO);
        }

        return "redirect:/article/list";
    }

}
