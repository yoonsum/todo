package com.example.todoapp.service;

import com.example.todoapp.domain.Label;
import com.example.todoapp.domain.Task;
import com.example.todoapp.dto.TaskDTO;
import com.example.todoapp.repository.LabelRepository;
import com.example.todoapp.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final LabelRepository labelRepository;

    /**
     * 할 일 생성하기
     * @param taskDTO
     */
    @Transactional
    public Long addTask(TaskDTO taskDTO){

        Label label = existenceLabel(taskDTO.getLabel_id());

        Task task = Task.builder()
                .title(taskDTO.getTitle())
                .content(taskDTO.getContent())
                .taskDate(taskDTO.getTaskDate())
                .priority(taskDTO.getTaskPriority())
                .label(label)
                .build();

        return taskRepository.addTask(task);
    }

    /**
     * 라벨에 대한 할일 조회
     * @param label_id
     * @return
     */
    public List<Task> readTaskByLabel(Long label_id){
        return taskRepository.readTaskByLabel(label_id);
    }

    /**
     * 할 일 수정하기
     * @param task_id
     * @param taskDTO
     */
    @Transactional
    public void updateTask(Long task_id, TaskDTO taskDTO){
        Task task = validateExistenceTask(task_id);

        Label label = existenceLabel(taskDTO.getLabel_id());

        taskRepository.updateTask(task, taskDTO, label);
    }

    /**
     * 할일 완료
     * @param task_id
     */
    @Transactional
    public void completeTask(Long task_id){
        Task task = validateExistenceTask(task_id);

        taskRepository.completeTask(task, LocalDate.now());
    }

    /**
     * 할 일 삭제
     * @param taskId
     */
    @Transactional
    public void deleteTask(Long taskId){
        taskRepository.deleteTask(taskId);
    }

    /**
     * 라벨 존재 유무 검증
     * @param label_id
     * @return
     */
    private Label existenceLabel(Long label_id){
        Optional<Label> optionalLabel = labelRepository.findByOne(label_id);

        if(optionalLabel.isEmpty()){
            throw new IllegalArgumentException("존재하지 않는 label입니다.");
        }
        return optionalLabel.get();
    }

    /**
     * 할일 존재 유뮤 검증
     * @param task_id
     * @return
     */
    private Task validateExistenceTask(Long task_id){
        Optional<Task> optionalLabel = taskRepository.readTask(task_id);
        if(optionalLabel.isEmpty()){
            throw new IllegalArgumentException("존재하지 않는 task입니다.");
        }
        return optionalLabel.get();
    }
}
