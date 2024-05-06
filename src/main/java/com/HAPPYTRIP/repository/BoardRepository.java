package com.HAPPYTRIP.repository;

import com.HAPPYTRIP.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findAll();

    Optional<Board> findById(Long id);

    void deleteById(Long id);

    Page<Board> findAll(Pageable pageable);

    @Query("select "
            + "distinct b "
            + "from Board b "
            + "left outer join Member u1 on b.memberId=u1 "
            + "left outer join Comment c on b.id= c.board.id "
            + "where "
            + "   b.title like %:kw% "
            + "   or b.content like %:kw% "
            + "   or u1.userId like %:kw% "
            + "   or c.content like %:kw% ")
    Page<Board> findAllByKeyword(@Param("kw") String kw, Pageable pageable);
}