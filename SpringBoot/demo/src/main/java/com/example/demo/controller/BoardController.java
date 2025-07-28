package com.example.demo.controller;

import com.example.demo.dto.BoardDto;
import com.example.demo.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/list")
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/view/create")
    public ResponseEntity<Long> add(@RequestBody BoardDto createDto) {
        Long id = boardService.addBoard(createDto);

        return ResponseEntity.ok(id);
    }

    @PatchMapping("/board/patch")
    public ResponseEntity<Long> patch(@RequestBody BoardDto patchDto) {
        Long id = boardService.patchBoard(patchDto);

        return ResponseEntity.ok(id);
    }



    @GetMapping("/view/{page}")
    public ResponseEntity<List<BoardDto>> list(@PathVariable Long page) {

        List<BoardDto> dtos = boardService.view(page);

        return ResponseEntity.ok()
                .body(dtos);
    }

}
