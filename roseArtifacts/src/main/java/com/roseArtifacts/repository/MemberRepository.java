package com.roseArtifacts.repository;

import com.roseArtifacts.entity.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByUserId(String userId);

    @EntityGraph(attributePaths = "memberRole")
    Member findWithRoseSetByUserId(String UserId);
}
