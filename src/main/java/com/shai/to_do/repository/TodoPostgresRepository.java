package com.shai.to_do.repository;

import com.shai.to_do.entity.Todo;
import jakarta.persistence.Table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Table(name = "todos")
public interface TodoPostgresRepository extends JpaRepository<Todo, Integer> {

    @Modifying
    @Transactional
    @Query("UPDATE todos t SET t.state = :state WHERE t.rawid = :id")
    void updateStateById(Integer id, String state);

    boolean existsTodoByTitle(String title);

    int countByState(String status);

    List<Todo> findAllByState(String status);


}
