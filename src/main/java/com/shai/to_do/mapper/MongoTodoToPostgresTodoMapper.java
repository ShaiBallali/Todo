package com.shai.to_do.mapper;

import com.shai.to_do.entity.MongoTodo;
import com.shai.to_do.entity.Todo;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class MongoTodoToPostgresTodoMapper implements Function<MongoTodo, Todo> {
    @Override
    public Todo apply(MongoTodo mongoTodo) {
        Todo todo = new Todo();
        todo.setRawid(mongoTodo.getRawid());
        todo.setTitle(mongoTodo.getTitle());
        todo.setContent(mongoTodo.getContent());
        todo.setDuedate(mongoTodo.getDuedate());
        todo.setState(mongoTodo.getState());
        return todo;
    }
}
