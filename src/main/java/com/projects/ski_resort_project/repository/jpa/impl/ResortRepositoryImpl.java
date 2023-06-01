package com.projects.ski_resort_project.repository.jpa.impl;


import com.projects.ski_resort_project.model.Resort;
import com.projects.ski_resort_project.repository.jpa.QPredicates;
import com.projects.ski_resort_project.repository.jpa.ResortRepositoryQueries;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.SimplePath;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import com.projects.ski_resort_project.model.QResort;

import static org.hibernate.jpa.HibernateHints.HINT_CACHEABLE;
import static org.hibernate.jpa.HibernateHints.HINT_FETCH_SIZE;

@Slf4j
@RequiredArgsConstructor
@Component
public class ResortRepositoryImpl implements ResortRepositoryQueries {
    private final EntityManager entityManager;

    @Override
    public Page<Resort> findByContinentAndTitle(String continent,
                                                String title,
                                                Pageable pageable) {
        Sort sort = pageable.getSort();
        Predicate predicate = QPredicates.builder()
                .add(title, QResort.resort.title::containsIgnoreCase)
                .add(continent, QResort.resort.city.country.continent.name::equalsIgnoreCase)
                .build();
        JPAQuery<Resort> resortJPAQuery = new JPAQuery<Resort>(entityManager)
                .select(QResort.resort)
                .from(QResort.resort)
                .where(predicate)
                .setHint(HINT_FETCH_SIZE, 100)
                .setHint(HINT_CACHEABLE, true)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(getSortedColumn(sort, Resort.class));
        return new PageImpl<>(resortJPAQuery.fetch(), pageable, resortJPAQuery.fetchCount());
    }

    private <T> OrderSpecifier<?>[] getSortedColumn(Sort sorts, Class<T> object) {
        return sorts.toList().stream().map(x -> {
            Order order = Order.valueOf(x.getDirection().name());
            SimplePath<Object> filedPath = Expressions.path(object, x.getProperty());
            return new OrderSpecifier(order, filedPath);
        }).toArray(OrderSpecifier[]::new);
    }
}
