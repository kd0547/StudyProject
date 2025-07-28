package com.example.boardexample.repository;

import com.example.boardexample.category.BoardCategory;
import com.example.boardexample.dto.BoardDto;
import com.example.boardexample.entity.Board;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class BoardRepository {

    @PersistenceContext
    private EntityManager em;

    public List<Board> findPage(int page) {
        return em.createQuery("select b from Board b order by createAt desc")
                .setMaxResults(10)
                .setFirstResult((page - 1) * 10)
                .getResultList();
    }

    public Long save(BoardDto boardDto) {
        Board board = Board.createBoard(boardDto);
        em.persist(board);
        return board.getId();
    }

    public Optional<Board> findById(Long id) {
        Board board = em.find(Board.class,id);
        Optional<Board> optionalBoard = Optional.ofNullable(board);
        return optionalBoard;
    }

    public int updateLike(long id) {
        Query query = em.createNativeQuery(
                "UPDATE board SET likes = likes + 1 WHERE id = :id")
                .setParameter("id",id);
        return query.executeUpdate();
    }

    public Long CountById() {
        return ((Number) em.createNativeQuery("SELECT next_val FROM board_seq")
                .getSingleResult())
                .longValue();
    }

    public List<Board> findPageByFilter(int page, String category) {
        BoardCategory boardCategory = BoardCategory.formString(category);

        return em.createQuery("select b from Board b where category =:category order by createAt desc ")
                .setParameter("category",boardCategory)
                .setFirstResult((page - 1) * 10)
                .setMaxResults(10)
                .getResultList();
    }

    public Long CountByIdFilter(String category) {
        BoardCategory boardCategory = BoardCategory.formString(category);


        return ((Number) em.createNativeQuery("SELECT COUNT(id) FROM board WHERE category=:category")
                .setParameter("category",category)
                .getSingleResult()
        ).longValue();
    }
}
