package com.example.boardexample.controller;

import com.example.boardexample.dto.BoardDto;
import com.example.boardexample.dto.BoardResponseDto;
import com.example.boardexample.service.BoardService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.boardexample.dto.PageDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping(value = "/list",produces = MediaType.TEXT_HTML_VALUE)
    public String view(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "ALL") String category,
            Model model) {
        BoardResponseDto boardResponseDto = boardService.view(page,category);
        List<BoardDto> boardList = boardResponseDto.getBoards();

        PageDto pageDto = new PageDto();
        pageDto.setTotalPage(boardResponseDto.getBoardCount());
        pageDto.setNumber((long)page);

        model.addAttribute("boardList",boardList);
        model.addAttribute("page",pageDto);
        model.addAttribute("choseCategory",category);

        return "boardView";
    }

    @ResponseBody
    @GetMapping(value = "/list",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,Object>> viewJson(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "ALL") String category
            ) {
        BoardResponseDto boardResponseDto =  boardService.view(page, category);
        PageDto pageDto = new PageDto();
        pageDto.setTotalPage(boardResponseDto.getBoardCount());
        pageDto.setNumber((long)page);

        Map<String,Object> response = new HashMap<>();
        response.put("boardList",boardResponseDto.getBoards());
        response.put("page",pageDto);

        return ResponseEntity.ok(response);
    }


    @GetMapping("/post/{id}")
    public String postView(
            HttpServletRequest request,
            @PathVariable Long id,
            @RequestParam int page,
            Model model) {

        boolean viewPost = Boolean.TRUE.equals(request.getAttribute("viewPost"));
        BoardDto boardDto = boardService.postView(id,viewPost);
        model.addAttribute("post",boardDto);
        model.addAttribute("page",page);

        return "postView";
    }


    @GetMapping("/board/new")
    public String create(Model model) {

        BoardDto boardDto = new BoardDto();
        boardDto.setAuthor("kimdongwook");

        model.addAttribute("boardDto",boardDto);
        return "postCreate";
    }

    @ResponseBody
    @PostMapping("/post/{id}/like")
    public ResponseEntity<String> addLikes(@PathVariable("id") long id) {
        boardService.postLiked(id);
        return ResponseEntity.ok("");
    }

    @PostMapping("/board/new")
    public String saveCreate(@ModelAttribute BoardDto boardDto) {
        Long id = boardService.createPost(boardDto);

        return "redirect:/list";
    }

}
