package com.example.todoapp.repository;

import com.example.todoapp.domain.Task;
import com.example.todoapp.domain.TaskPriority;
import com.example.todoapp.domain.TaskStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class TaskRepositoryTest {
    @Autowired TaskRepository taskRepository;

    @Test
    @Rollback(value = false)
    void 할일추가() throws Exception {
        //given
        Task task1 = new Task();
        task1.setTitle("todo1");
        task1.setTaskDate(LocalDate.of(2024,1,1));
        task1.setTaskPriority(TaskPriority.LOW);
        task1.setTaskStatus(TaskStatus.DOING);

        Task task2 = new Task();
        task2.setTitle("todo2");
        task2.setTaskPriority(TaskPriority.LOW);
        task2.setTaskStatus(TaskStatus.DOING);
        task1.setRelation(task2);

        //when
        Long taskId1 = taskRepository.addTask(task1);
        Long taskId2 = taskRepository.addTask(task2);

        //then
        assertThat(task1.getId()).isEqualTo(taskId1);
        assertThat(task2.getId()).isEqualTo(taskId2);
        assertThat(task2.getParentTask()).isEqualTo(task1);
        assertThat(task1.getSubTasks().size()).isEqualTo(1);
    }

    @Test
    @Rollback(value = false)
    void 할일삭제_cascade() throws Exception{
        //given
        Task task1 = new Task();
        task1.setTitle("todo1");
        task1.setTaskPriority(TaskPriority.LOW);
        task1.setTaskStatus(TaskStatus.DOING);

        Task task2 = new Task();
        task2.setTitle("todo2");
        task2.setTaskPriority(TaskPriority.LOW);
        task2.setTaskStatus(TaskStatus.DOING);
        task1.setRelation(task2);

        Long taskId1 = taskRepository.addTask(task1);
        Long taskId2 = taskRepository.addTask(task2);

        //when
        taskRepository.deleteTask(taskId1);

        //then
        assertThat(taskRepository.readTask().size()).isEqualTo(0);
    }

    @Test
    @Rollback(value = false)
    void 할일삭제() throws Exception{
        //given
        Task task1 = new Task();
        task1.setTitle("todo1");
        task1.setTaskPriority(TaskPriority.LOW);
        task1.setTaskStatus(TaskStatus.DOING);

        Task task2 = new Task();
        task2.setTitle("todo2");
        task2.setTaskPriority(TaskPriority.LOW);
        task2.setTaskStatus(TaskStatus.DOING);
        task1.setRelation(task2);

        Long taskId1 = taskRepository.addTask(task1);
        Long taskId2 = taskRepository.addTask(task2);

        //when
        taskRepository.deleteTask(taskId2);

        //then
        assertThat(taskRepository.readTask().size()).isEqualTo(1);
    }
}