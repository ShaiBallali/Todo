package com.shai.to_do.repository;

import com.shai.to_do.entity.MongoTodo;
import com.shai.to_do.entity.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

public class TodoRepositoryCustomImpl implements TodoRepositoryCustom {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void updateStateById(Integer id, String state) {
        Query query = new Query(Criteria.where("rawid").is(id));
        Update update = new Update().set("state", state);
        mongoTemplate.updateFirst(query, update, MongoTodo.class);
    }

    @Override
    public void deleteByCustomId(Integer id) {
        Query query = new Query(Criteria.where("rawid").is(id));
        mongoTemplate.remove(query, MongoTodo.class);
    }
}
