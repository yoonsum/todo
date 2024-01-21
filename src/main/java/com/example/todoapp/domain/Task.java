package com.example.todoapp.domain;

import jakarta.persistence.*;
import lombok.Builder;
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
    @JoinColumn(name="label_id")
    private Label label;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_task_id")
    private Task parentTask;

    @OneToMany(mappedBy = "parentTask")
    private List<Task> subTasks = new ArrayList<>();

    @Builder
    public Task(String title, String content, LocalDate taskDate, TaskPriority priority, Label label){
        this.title = title;
        this.content = content;
        this.taskDate = taskDate;
        this.taskPriority = priority;
        this.label = label;
        this.taskStatus = TaskStatus.DOING;
    }

    // 할 일 수정
    public void updateTask(String title, String content, LocalDate taskDate, TaskPriority priority, Label label){
        this.title = title;
        this.content = content;
        this.taskDate = taskDate;
        this.taskPriority = priority;
        this.label = label;
    }

    // 할 일 완료
    public void completedTask(LocalDate completedDate){
        this.taskStatus = TaskStatus.DONE;
        this.completedDate = completedDate;
    }

    //===연관관계 메소드===//
    public void setRelation(Task subTask){
        this.getSubTasks().add(subTask);
        subTask.setParentTask(this);
    }

    public void deleteRelation(){
        // 자식들과 연관관계 삭제
        List<Task> subs = this.getSubTasks();
        if(!subs.isEmpty()){
            for(Task s : subs){
                s.setParentTask(null);
            }
        }

        // 부모와 연관관계 삭제
        Task parent = this.getParentTask();
        if(parent != null){
            parent.getSubTasks().remove(this);
            this.setParentTask(null);
        }
    }
}
