package com.city.traffic.toll.fee.calculator.common.model.model.repository;

import com.city.traffic.toll.fee.calculator.common.model.model.entity.EntityBase;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface BaseRepository<T extends EntityBase> extends JpaRepository<T, Long>, JpaSpecificationExecutor<T> {
    default void delete(T entity) {
        delete(entity);
    }

    default T save(T entity, Long userId) {
        entity.setCreatedBy(userId);
        entity.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        return save(entity);
    }

    default List<T> save(List<T> entities, Long userId) {
        List<T> outputEntities = new ArrayList<>();
        for(T entity : entities){
            outputEntities.add(save(entity, userId)) ;
        }
        return outputEntities;
    }

    default T update(T entity, Long userId) {
        entity.setLastModifiedBy(userId);
        entity.setLastModifiedAt(Timestamp.valueOf(LocalDateTime.now()));
        return save(entity);
    }

    default void deleteAllInBatch(Iterable<T> entities, Long userId) {
        for (T entity : entities) {
            delete(entity);
        }
    }

    default void deleteById(Long id, Long userId) {
        T entity = findById(id)
                .orElseThrow(() -> new EmptyResultDataAccessException(
                        String.format("No %s entity with id %s exists",
                                this.getClass().getTypeParameters()[0].getName(), id),
                        1));
        delete(entity);
    }

    default void deleteAllInBatchById(Iterable<Long> ids, Long userId) {
        for (Long id : ids) {
            deleteById(id, userId);
        }
    }
}