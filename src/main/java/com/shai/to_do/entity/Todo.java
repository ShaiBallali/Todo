package com.shai.to_do.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "todos")
public class Todo {
    @Id
    private Integer rawid;
    private String title;
    private String content;
    private String state;
    private Long duedate;
}
