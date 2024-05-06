package com.HAPPYTRIP.repository;

import com.HAPPYTRIP.domain.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {

    List<Notice> findAll();

    Optional<Notice> findById(Long id);

    @Query("select "
            + "distinct n "
            + "from Notice n "
            + "where "
            + "   n.title like %:kw% "
            + "   or n.content like %:kw% ")
    Page<Notice> findAllByKeyword(@Param("kw") String kw, Pageable pageable);
}