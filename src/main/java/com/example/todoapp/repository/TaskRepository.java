package com.example.todoapp.repository;

import com.example.todoapp.domain.Label;
import com.example.todoapp.domain.Task;
import com.example.todoapp.dto.TaskDTO;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TaskRepository {
    private final EntityManager em;

    public Long addTask(Task task){
        em.persist(task);
        return task.getId();
    }

    public void updateTask(Task origin, TaskDTO update, Label label){
        origin.updateTask(update.getTitle(), update.getContent(), update.getTaskDate(), update.getTaskPriority(), label);
    }

    public void completeTask(Task task, LocalDate date){
        task.completedTask(date);
    }

    public Optional<Task> readTask(Long task_id){
        return Optional.ofNullable(em.find(Task.class, task_id));
    }

    public List<Task> readTaskByLabel(Long label_id){
        return em.createQuery("select t from Task t where t.label.id = :label_id", Task.class)
                .setParameter("label_id", label_id)
                .getResultList();
    }

    public void deleteTask(Long taskId){
        Task task = em.find(Task.class, taskId);
        if(task != null){
            task.deleteRelation();
            em.remove(task);
        }
    }
}
