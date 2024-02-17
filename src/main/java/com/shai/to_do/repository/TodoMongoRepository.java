package com.shai.to_do.repository;

import com.shai.to_do.entity.MongoTodo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoMongoRepository extends MongoRepository<MongoTodo, Integer>, TodoRepositoryCustom {

    void updateStateById(Integer id, String state);

    boolean existsTodoByTitle(String title);

    int countByState(String status);

    List<MongoTodo> findAllByState(String status);
}
