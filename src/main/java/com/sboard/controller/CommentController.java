package com.sboard.controller;

import com.sboard.dto.CommentDTO;
import com.sboard.entity.Comment;
import com.sboard.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Log4j2
@RequiredArgsConstructor
@Controller
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comment/write")
    public ResponseEntity<CommentDTO> write(@RequestBody CommentDTO commentDTO, HttpServletRequest req) {

        String regip = req.getRemoteAddr();
        commentDTO.setRegip(regip);
        log.info(commentDTO);

        CommentDTO dto = commentService.insertComment(commentDTO);

        return ResponseEntity.ok().body(dto);

    }

    @GetMapping("/comment/delete")
    public String delete(int no, int aNo){

        log.info("nnooo"+no);
        commentService.deleteComment(no);

        return "redirect:/article/view?no="+aNo;
    }

}
