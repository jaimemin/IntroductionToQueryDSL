package com.tistory.jaimemin.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tistory.jaimemin.querydsl.entity.Hello;
import com.tistory.jaimemin.querydsl.entity.QHello;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class QuerydslApplicationTests {

    @PersistenceContext
    EntityManager entityManager;

    @Test
    void contextLoads() {
        Hello hello = new Hello();
        entityManager.persist(hello);

        JPAQueryFactory query = new JPAQueryFactory(entityManager);
        QHello qHello = new QHello("h");

        Hello result = query
                .selectFrom(qHello)
                .fetchOne();

        assertThat(result).isEqualTo(hello);
        assertThat(result.getId()).isEqualTo(hello.getId());
    }

}
