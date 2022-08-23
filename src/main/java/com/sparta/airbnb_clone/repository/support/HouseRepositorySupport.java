package com.sparta.airbnb_clone.repository.support;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.airbnb_clone.domain.House;
import com.sparta.airbnb_clone.shared.Category;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.sparta.airbnb_clone.domain.QHouse.house;

@Repository
public class HouseRepositorySupport extends QuerydslRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public HouseRepositorySupport(JPAQueryFactory queryFactory) {
        super(House.class);
        this.queryFactory = queryFactory;
    }

    public List<House> findAllByCategory(Category category) {
        return queryFactory
                .selectFrom(house)
                .where(house.category.eq(category))
                .fetch();
    }

}
