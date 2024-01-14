package com.example.todoapp.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="task_id")
    private Long id;

    private String title;

    private String content;

    private LocalDate taskDate;

    @Enumerated(EnumType.STRING)
    private TaskPriority taskPriority;

    @Enumerated(EnumType.STRING)
    private TaskStatus taskStatus;

    private LocalDate completedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_task_id")
    private Task parentTask;

    @OneToMany(mappedBy = "parentTask", cascade = CascadeType.ALL)
    private List<Task> subTasks = new ArrayList<>();

    //===연관관계 메소드===//
    public void setRelation(Task subTask){
        this.getSubTasks().add(subTask);
        subTask.setParentTask(this);
    }

    public void deleteRelation(){
        Task parent = this.getParentTask();
        if(parent != null){
            parent.getSubTasks().remove(this);
            this.setParentTask(null);
        }
    }
}
