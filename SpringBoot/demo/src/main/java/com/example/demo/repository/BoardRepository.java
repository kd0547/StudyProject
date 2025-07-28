package com.example.demo.repository;

import com.example.demo.Entity.Board;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class BoardRepository {

    @PersistenceContext
    private EntityManager em;

    public List<Board> findPage(Long page) {
        Query pageQuery = em.createQuery("select b from Board b order by createAt desc");
        pageQuery.setMaxResults(10);
        pageQuery.setFirstResult((int) ((page - 1) * 10));
        List<Board> boardList = pageQuery.getResultList();

        return boardList;
    }

    public Long save(String title, String subject, String author) {

        LocalDateTime now = LocalDateTime.now();

        Board board = new Board();
        board.setTitle(title);
        board.setSubject(subject);
        board.setAuthor(author);

        board.setCreateAt(now);
        board.setUpdateAt(now);

        em.persist(board);
        return board.getId();
    }

    public Long patch(Long id,String title, String subject) {

        Board board = em.find(Board.class,id);


        return 1L;
    }
}
