package com.example.demo.service;

import com.example.demo.Entity.Board;
import com.example.demo.dto.BoardDto;
import com.example.demo.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    public List<BoardDto> view(Long page) {

        List<Board> boards = boardRepository.findPage(page);

        List<BoardDto> boardDtos = new ArrayList<>();

        boards.forEach(board -> {
            BoardDto boardDto = BoardDto.createDto(board);
            boardDtos.add(boardDto);
        });

        return boardDtos;
    }

    @Transactional
    public Long addBoard(BoardDto createDto) {
        return boardRepository.save(createDto.getTitle(),createDto.getSubject(),createDto.getAuthor());
    }

    @Transactional
    public Long patchBoard(BoardDto patchDto) {
        return boardRepository.patch(patchDto.getId(),patchDto.getTitle(),patchDto.getSubject());
    }
}
