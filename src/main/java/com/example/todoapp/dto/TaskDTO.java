package com.example.todoapp.dto;

import com.example.todoapp.domain.Label;
import com.example.todoapp.domain.TaskPriority;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class TaskDTO {
    @NotEmpty(message = "title은 필수입니다.")
    private String title;

    private String content;

    private LocalDate taskDate;

    private TaskPriority taskPriority;

    private Long label_id;
}
