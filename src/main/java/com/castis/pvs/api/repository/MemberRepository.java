package com.castis.pvs.api.repository;

import com.castis.pvs.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("SELECT m FROM Member m WHERE m.so_happycall_auth = :soHappycallAuth AND m.create_time >= :startTime AND m.create_time <= :endTime AND m.so_id = :soId")
    Page<Member> findAllBySoHappycallAuthAndCreateTimeBetween(@Param("startTime") Timestamp searchStarttime , @Param("endTime") Timestamp searchEndTime , @Param("soHappycallAuth") Integer soHappycallAuth ,@Param("soId")  String soId, Pageable pageable);

    @Query("SELECT m FROM Member m WHERE m.member_id = :memberId AND m.ci = :ci")
    Optional<Member> findByMemberIdAndCi(@Param("memberId") String memberId,@Param("ci") String ci);
}

