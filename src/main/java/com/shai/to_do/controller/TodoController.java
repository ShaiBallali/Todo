package com.shai.to_do.controller;

import com.shai.to_do.dto.TodoDTO;
import com.shai.to_do.dto.response.*;
import com.shai.to_do.exception.DueDateExpiredException;
import com.shai.to_do.exception.BadRequestException;
import com.shai.to_do.exception.ResourceNotFoundException;
import com.shai.to_do.exception.TodoAlreadyExistsException;
import com.shai.to_do.service.TodoService;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/todo")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping("/health")
    public String health() {
        return "OK";
    }

    @PostMapping
    public ResponseEntity<AddResponseDTO> add(@RequestBody TodoDTO todoDTO) throws TodoAlreadyExistsException, DueDateExpiredException {
        return new ResponseEntity<>(todoService.add(todoDTO), HttpStatus.OK);
    }

    @GetMapping("/size")
    public ResponseEntity<CountByStatusResponseDTO> countByStatus(@RequestParam String status, @RequestParam String persistenceMethod) throws BadRequestException {
        return new ResponseEntity<>(todoService.countByStatus(status, persistenceMethod), HttpStatus.OK);
    }

    @GetMapping("/content")
    public ResponseEntity<GetContentResponseDTO> getTodoContentByStatusSortedByField(@RequestParam String status,
                                                                     @RequestParam Optional<String> sortBy, @RequestParam String persistenceMethod) throws BadRequestException {
        return new ResponseEntity<>(todoService.getTodoContentByStatusSortedByField(status, sortBy, persistenceMethod), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<UpdateStatusResponseDTO> updateStatus(@RequestParam Integer id,
                                                @RequestParam String status) throws BadRequestException, ResourceNotFoundException {
        return new ResponseEntity<>(todoService.updateStatus(id, status), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<DeleteResponseDTO> deleteTodo(@RequestParam Integer id) throws ResourceNotFoundException {
        return new ResponseEntity<>(todoService.deleteById(id), HttpStatus.OK);
    }

}
