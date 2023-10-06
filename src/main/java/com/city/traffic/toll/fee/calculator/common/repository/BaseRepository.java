package com.city.traffic.toll.fee.calculator.common.repository;

import com.city.traffic.toll.fee.calculator.common.model.model.EntityBase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@NoRepositoryBean
public interface BaseRepository<T extends EntityBase> extends JpaRepository<T, Long>, JpaSpecificationExecutor<T> {
    default void deleteEntity(T entity) {
        delete(entity);
    }

    default T save(T entity, Long userId) {
        entity.setCreatedBy(userId);
        entity.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        return save(entity);
    }

    default T update(T entity, Long userId) {
        entity.setLastModifiedBy(userId);
        entity.setLastModifiedAt(Timestamp.valueOf(LocalDateTime.now()));
        return save(entity);
    }

}