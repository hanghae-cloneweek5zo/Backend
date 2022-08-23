package com.sparta.airbnb_clone.repository.support;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.airbnb_clone.domain.House;
import com.sparta.airbnb_clone.shared.Authority;
import com.sparta.airbnb_clone.shared.Category;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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

//    public List<House> findAllByUsers(String name, String username, String joinedDate, Status status, int pageIndex, int pageSize){
//        return queryFactory
//                .selectFrom(user)
//                .where(user.authority.eq(Authority.ROLE_USER),
//                        eqName(name),
//                        eqUsername(username),
//                        eqCreatedAt(joinedDate),
//                        eqStatus(status))
//                .orderBy(user.createdAt.desc())
//                .offset(pageIndex * pageSize)
//                .limit(pageSize)
//                .fetch();
//    }
//
//    private BooleanExpression eqName(String name){
//        if(!StringUtils.hasText(name))  return null;
//        return user.name.eq(name);
//    }
//
//    private BooleanExpression eqUsername(String username){
//        if(!StringUtils.hasText(username))  return null;
//        return user.username.eq(username);
//    }
//
//    private BooleanExpression eqCreatedAt(String joinedDate) {
//        if (!StringUtils.hasText(joinedDate)) return null;
//        else {
//            LocalDate date = LocalDate.parse(joinedDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//            return user.createdAt.between(date.atStartOfDay(), LocalDateTime.of(date, LocalTime.MAX));
//
//        }
//    }
//    private BooleanExpression eqStatus(Status status){
//        if(status==null)    return null;
//        else if(!StringUtils.hasText(status.toString())) return null;
//        return user.status.eq(status);
//    }

}
