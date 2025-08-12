package com.config.querydsl.service1.service.impl;

import com.config.querydsl.service1.model.entity.MyEntity;
import com.config.querydsl.service1.model.entity.QMyEntity;
import com.config.querydsl.service1.service.MyService;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Service;

@Service
public class MyServiceImpl implements MyService {

    private final JPAQueryFactory queryFactory;

    public MyServiceImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public void readService() {

        QMyEntity myEntity = QMyEntity.myEntity; // Q클래스는 보통 소문자 시작 static 인스턴스 제공

        // 예: 조건에 맞는 엔티티 조회
        MyEntity result = queryFactory
                .selectFrom(myEntity)
                .where(myEntity.notice.eq("myNotice"))
                .fetchOne();

        System.out.println(result);

    }

    @Override
    public void createService() {

    }

    @Override
    public void updateService() {

    }

    @Override
    public void deleteService() {

    }
}
