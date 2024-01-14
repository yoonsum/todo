package com.example.todoapp.repository;

import com.example.todoapp.domain.Task;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TaskRepository {
    private final EntityManager em;

    public Long addTask(Task task){
        em.persist(task);
        return task.getId();
    }

    public List<Task> readTask(){
        return em.createQuery("select t from Task t", Task.class).getResultList();
    }

    public void deleteTask(Long taskId){
        Task task = em.find(Task.class, taskId);
        task.deleteRelation();
        em.remove(task);
    }
}
