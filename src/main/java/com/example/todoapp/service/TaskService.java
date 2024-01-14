package com.example.todoapp.service;

import com.example.todoapp.domain.Task;
import com.example.todoapp.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;

    @Transactional
    public void addTask(Task task){
        taskRepository.addTask(task);
    }

    @Transactional
    public void deleteTask(Long taskId){
        taskRepository.deleteTask(taskId);
    }
}
