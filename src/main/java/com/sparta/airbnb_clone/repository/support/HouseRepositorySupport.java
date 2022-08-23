package com.sparta.airbnb_clone.repository.support;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.airbnb_clone.domain.House;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.sparta.airbnb_clone.domain.QHouse.house;

@Repository
public class HouseRepositorySupport extends QuerydslRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public HouseRepositorySupport(JPAQueryFactory queryFactory) {
        super(House.class);
        this.queryFactory = queryFactory;
    }

    public List<House> findByName(String name) {
        return queryFactory
                .selectFrom(house)
                .fetch();
    }

}
