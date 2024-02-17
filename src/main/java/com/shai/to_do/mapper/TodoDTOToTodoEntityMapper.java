package com.shai.to_do.mapper;

import com.shai.to_do.constants.Status;
import com.shai.to_do.dto.TodoDTO;
import com.shai.to_do.entity.MongoTodo;
import com.shai.to_do.entity.Todo;
import com.shai.to_do.repository.TodoMongoRepository;
import com.shai.to_do.repository.TodoPostgresRepository;
import org.springframework.stereotype.Component;

@Component
public class TodoDTOToTodoEntityMapper {

    private final TodoPostgresRepository postgresRepository;
    private final TodoMongoRepository mongoRepository;

    public TodoDTOToTodoEntityMapper(TodoPostgresRepository postgresRepository, TodoMongoRepository mongoRepository) {
        this.postgresRepository = postgresRepository;
        this.mongoRepository = mongoRepository;
    }

    public Todo mapToPostgres(TodoDTO todoDTO) {
        Integer id = (int) postgresRepository.count() + 1;
        String title = todoDTO.title();
        String content = todoDTO.content();
        String status = Status.PENDING;
        Long dueDate = todoDTO.dueDate();

        return new Todo(id, title, content, status, dueDate);
    }

    public MongoTodo mapToMongo(TodoDTO todoDTO) {
        Integer id = (int) mongoRepository.count() + 1;
        String title = todoDTO.title();
        String content = todoDTO.content();
        String status = Status.PENDING;
        Long dueDate = todoDTO.dueDate();

        return new MongoTodo(id, title, content, status, dueDate);
    }
}
