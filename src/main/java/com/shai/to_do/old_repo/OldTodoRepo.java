package com.shai.to_do.old_repo;

import com.shai.to_do.constants.PersistenceMethod;
import com.shai.to_do.constants.SortBy;
import com.shai.to_do.constants.Status;
import com.shai.to_do.entity.MongoTodo;
import com.shai.to_do.entity.Todo;
import com.shai.to_do.exception.ResourceNotFoundException;
import com.shai.to_do.mapper.MongoTodoToPostgresTodoMapper;
import com.shai.to_do.repository.TodoMongoRepository;
import com.shai.to_do.repository.TodoPostgresRepository;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;


@Component
public class OldTodoRepo {
    private final TodoPostgresRepository postgresRepository;
    private final TodoMongoRepository mongoRepository;
    private final MongoTodoToPostgresTodoMapper mongoTodoToPostgresTodoMapper;

    public OldTodoRepo(TodoPostgresRepository postgresRepository, TodoMongoRepository mongoRepository, MongoTodoToPostgresTodoMapper mongoTodoToPostgresTodoMapper) {
        this.postgresRepository = postgresRepository;
        this.mongoRepository = mongoRepository;
        this.mongoTodoToPostgresTodoMapper = mongoTodoToPostgresTodoMapper;
    }

    public void add(Todo todo, MongoTodo mongoTodo) {
        postgresRepository.save(todo);
        mongoRepository.save(mongoTodo);
    }

    public boolean existsById(Integer id) {
        return postgresRepository.existsById(id);
    }

    public void updateStatusById(Integer id, String status) {
        postgresRepository.updateStateById(id, status);
        mongoRepository.updateStateById(id, status);
    }

    public Todo findById(Integer id) throws ResourceNotFoundException {
        return postgresRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Error: no such TODO with id " + id));
    }

    public boolean existsByTitle(String title) {
        return postgresRepository.existsTodoByTitle(title);
    }

    public long countByStatus(String status, String persistenceMethod) {
        if (status.equals(Status.ALL)) {
            switch (persistenceMethod) {
                case PersistenceMethod.POSTGRES -> { return postgresRepository.count(); }
                case PersistenceMethod.MONGO -> { return mongoRepository.count(); }
            }

        }
        return postgresRepository.countByState(status);
    }

    public List<Todo> findTodoContentByStatusSortedByField(String status, String sortBy, String persistenceMethod) {
        List<Todo> filteredTodos = findTodoContentByStatus(status, persistenceMethod);

        switch (sortBy) {
            case SortBy.ID -> { sortById(filteredTodos); }
            case SortBy.TITLE -> { sortByTitle(filteredTodos); }
            case SortBy.DUE_DATE -> { sortByDueDate(filteredTodos); }
        }

        return filteredTodos;
    }

    public List<Todo> findTodoContentByStatus(String status, String persistenceMethod) {
        if (status.equals(Status.ALL)) {
            switch (persistenceMethod) {
                case PersistenceMethod.POSTGRES -> { return postgresRepository.findAll(); }
                case PersistenceMethod.MONGO -> { return mongoRepository.findAll()
                        .stream()
                        .map(mongoTodoToPostgresTodoMapper)
                        .collect(Collectors.toList()); }
            }
        }
        switch (persistenceMethod) {
            case PersistenceMethod.POSTGRES -> { return postgresRepository.findAllByState(status); }
            case PersistenceMethod.MONGO -> { return mongoRepository.findAllByState(status)
                    .stream()
                    .map(mongoTodoToPostgresTodoMapper)
                    .collect(Collectors.toList()); }
        }
        return null;
    }

    public int deleteById(Integer id) {
        postgresRepository.deleteById(id);
        mongoRepository.deleteByCustomId(id);
        return (int) postgresRepository.count();
    }

    private void sortById(List<Todo> todoList) {
        todoList.sort(new Comparator<Todo>() {
            @Override
            public int compare(Todo t1, Todo t2) {
                return t1.getRawid().compareTo(t2.getRawid());
            }
        });
    }

    private void sortByTitle (List<Todo> todoList) {
        todoList.sort(new Comparator<Todo>() {
            @Override
            public int compare(Todo t1, Todo t2) {
                return t1.getTitle().compareTo(t2.getTitle());
            }
        });
    }

    private void sortByDueDate (List<Todo> todoList) {
        todoList.sort(new Comparator<Todo>() {
            @Override
            public int compare(Todo t1, Todo t2) {
                return t1.getDuedate().compareTo(t2.getDuedate());
            }
        });
    }
}
