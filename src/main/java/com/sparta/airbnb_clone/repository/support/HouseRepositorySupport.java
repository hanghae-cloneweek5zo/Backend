package com.sparta.airbnb_clone.repository.support;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.airbnb_clone.domain.Facility;
import com.sparta.airbnb_clone.domain.House;
import com.sparta.airbnb_clone.shared.Authority;
import com.sparta.airbnb_clone.shared.Category;
import com.sparta.airbnb_clone.shared.FacilityType;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.sparta.airbnb_clone.domain.QFacility.facility;
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

    public List<House> findAllByFilter(int minPrice, int maxPrice,
                                       int bedRoomCnt, int bedCnt, List<FacilityType> facilities){
        return queryFactory
                .selectFrom(house)
                .leftJoin(facility)
                .where(betweenPrice(minPrice, maxPrice),
                        eqBedRoomCnt(bedRoomCnt),
                        eqBedCnt(bedCnt),
                        eqFacilities(facilities))
                .orderBy(house.createdAt.desc())
//                .offset(pageIndex * pageSize)
//                .limit(pageSize)
                .fetch();
    }

    private BooleanExpression betweenPrice(int minPrice, int maxPrice) {
        if(minPrice == 0 && maxPrice == 0) return null;
        return house.price.between(minPrice, maxPrice);
    }

    private BooleanExpression eqBedRoomCnt(int bedRoomCnt){
        if(bedRoomCnt == 0)  return null;
        return house.bedRoomCnt.eq(bedRoomCnt);
    }

    private BooleanExpression eqBedCnt(int bedCnt) {
        if(bedCnt == 0)  return null;
        return house.bedRoomCnt.eq(bedCnt);
    }

    private BooleanExpression eqFacilities(List<FacilityType> facilities){
        if(facilities == null) return null;
        return facility.facilityType.in(facilities);
    }

}
