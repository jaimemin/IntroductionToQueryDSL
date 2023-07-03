package com.tistory.jaimemin.querydsl.repository;

import com.tistory.jaimemin.querydsl.dto.MemberSearchCondition;
import com.tistory.jaimemin.querydsl.dto.MemberTeamDto;
import com.tistory.jaimemin.querydsl.entity.Member;
import com.tistory.jaimemin.querydsl.entity.Team;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class MemberJpaRepositoryTest {

    @Autowired
    EntityManager entityManager;

    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Test
    public void basicTest() {
        Member member = new Member("memberA", 10);
        memberJpaRepository.save(member);

        Member findMember = memberJpaRepository.findById(member.getId()).get();
        assertThat(findMember).isEqualTo(member);

        List<Member> result = memberJpaRepository.findAll();
        assertThat(result).containsExactly(member);

        List<Member> result2 = memberJpaRepository.findByUsername("memberA");
        assertThat(result2).containsExactly(member);
    }

    @Test
    public void basicQueryDslTest() {
        Member member = new Member("memberA", 10);
        memberJpaRepository.save(member);

        Member findMember = memberJpaRepository.findById(member.getId()).get();
        assertThat(findMember).isEqualTo(member);

        List<Member> result = memberJpaRepository.fidnAllQueryDsl();
        assertThat(result).containsExactly(member);

        List<Member> result2 = memberJpaRepository.findByUsernameQueryDsl("memberA");
        assertThat(result2).containsExactly(member);
    }

    @Test
    public void searchTest() {
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

        MemberSearchCondition condition = new MemberSearchCondition();
        condition.setAgeGoe(35);
        condition.setAgeLoe(40);
        condition.setTeamName("teamB");

        List<MemberTeamDto> result = memberJpaRepository.searchByBuilder(condition);

        assertThat(result).extracting("username").containsExactly("memberD");
    }

    @Test
    public void searchTest2() {
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

        MemberSearchCondition condition = new MemberSearchCondition();
        condition.setAgeGoe(35);
        condition.setAgeLoe(40);
        condition.setTeamName("teamB");

        List<MemberTeamDto> result = memberJpaRepository.search(condition);

        assertThat(result).extracting("username").containsExactly("memberD");
    }
}