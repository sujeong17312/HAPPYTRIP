package com.HAPPYTRIP.repository;

import com.HAPPYTRIP.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByUserId(String userId);
    Optional<Member> findByName(String name);
    void deleteByUserId(String userId);

    @Query("select "
            + "distinct m "
            + "from Member m "
            + "where "
            + "   m.userId like %:kw% "
            + "   or m.name like %:kw% "
            + "   or m.phone like %:kw% ")
    Page<Member> findAllByKeyword(@Param("kw") String kw, Pageable pageable);
}