package com.tistory.jaimemin.querydsl.entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class MemberTest {

    @PersistenceContext
    EntityManager entityManager;

    @Test
    public void testEntity() {
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        entityManager.persist(teamA);
        entityManager.persist(teamB);

        Member memberA = new Member("memberA", 10, teamA);
        Member memberB = new Member("memberB", 20, teamA);
        Member memberC = new Member("memberC", 30, teamB);
        Member memberD = new Member("memberD", 40, teamB);
        entityManager.persist(memberA);
        entityManager.persist(memberB);
        entityManager.persist(memberC);
        entityManager.persist(memberD);

        // 초기화
        entityManager.flush();
        entityManager.clear();

        List<Member> members = entityManager.createQuery("SELECT m FROM Member m", Member.class)
                .getResultList();

        assertThat(members.size()).isEqualTo(4);
    }
}