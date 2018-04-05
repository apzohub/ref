package com.abc;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;
import java.util.List;

public interface Repository extends JpaRepository<Entity, String>/*, QueryDslPredicateExecutor<Entity>*/ {

    @Query(value = "SELECT * FROM ENTITY WHERE NAME = ?1  ORDER BY ?#{#pageable}",
            countQuery = "SELECT count(*) FROM ENTITY WHERE NAME = ?1  ORDER BY ?#{#pageable}",
            nativeQuery = true)
    Page<Entity> findByName(String name, Pageable pageable);

    @Query(value = "SELECT * FROM ENTITY WHERE NAME = ?1", nativeQuery = true)
    List<Entity> findByName(String name);

    @Query(value = "SELECT * FROM ENTITY WHERE kv->>'attrib' = ?1", nativeQuery = true)
    List<Entity> findByAttrib(String att);

/*
    @Transactional
    @Query(value = "SELECT * FROM ENTITY WHERE kv @> ?1", nativeQuery = true)
    void deleteByName(String name);

    @Lock(LockModeType.OPTIMISTIC)
    void deleteByNameLocked(String name);
*/
}