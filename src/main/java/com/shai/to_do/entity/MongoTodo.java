package com.shai.to_do.entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Document(collection = "todos")
public class MongoTodo {
    @Id
    private Integer rawid;
    private String title;
    private String content;
    private String state;
    private Long duedate;
}
