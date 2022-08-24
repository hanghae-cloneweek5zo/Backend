package com.sparta.airbnb_clone.repository.support;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.airbnb_clone.domain.House;
import com.sparta.airbnb_clone.dto.response.HouseMainResponseDto;
import com.sparta.airbnb_clone.shared.Category;
import com.sparta.airbnb_clone.shared.FacilityType;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import java.util.List;

import static com.sparta.airbnb_clone.domain.QFacility.facility;
import static com.sparta.airbnb_clone.domain.QHouse.house;
import static com.sparta.airbnb_clone.domain.QHouseImg.houseImg;

@Repository
public class HouseRepositorySupport extends QuerydslRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public HouseRepositorySupport(JPAQueryFactory queryFactory) {
        super(House.class);
        this.queryFactory = queryFactory;
    }

    public List<HouseMainResponseDto> findAllByCategory(Category category) {
        return queryFactory
                .select(Projections.fields(
                        HouseMainResponseDto.class,
                        house.houseId,
                        house.category,
                        house.title,
                        house.nation,
                        house.price,
                        house.starAvg,
                        houseImg.imgUrl
                ))
                .from(house)
                .leftJoin(houseImg)
                .on(house.houseId.eq(houseImg.HouseImgId))
                .where(house.category.eq(category))
                .fetch();
    }
    public List<HouseMainResponseDto> findAllByOrderByModifiedAtDesc(){
        return queryFactory
                .select(Projections.fields(
                        HouseMainResponseDto.class,
                        house.houseId,
                        house.category,
                        house.title,
                        house.nation,
                        house.price,
                        house.starAvg,
                        houseImg.imgUrl
                ))
                .from(house)
                .leftJoin(houseImg)
                .on(house.houseId.eq(houseImg.HouseImgId))
                .fetch();
    }
    public List<HouseMainResponseDto> findAllByFilter(int minPrice, int maxPrice,
                                                      int bedRoomCnt, int bedCnt, List<FacilityType> facilities){

        return queryFactory
                .select(Projections.fields(
                        HouseMainResponseDto.class,
                        house.houseId,
                        house.category,
                        house.title,
                        house.nation,
                        house.price,
                        house.starAvg,
                        houseImg.imgUrl
                ))
                .from(house)
                .leftJoin(facility)
                .on(house.houseId.eq(facility.house.houseId))
                .leftJoin(houseImg)
                .on(house.houseId.eq(houseImg.house.houseId))
                .where(betweenPrice(minPrice, maxPrice),
                        eqBedRoomCnt(bedRoomCnt),
                        eqBedCnt(bedCnt),
                        eqFacilities(facilities))
                .orderBy(house.createdAt.desc())
//                .offset(pageable.getOffset())
//                .limit(pageable.getPagesize)
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
        if(facilities.isEmpty()) return null;
        return facility.facilityType.in(facilities);
    }
}
