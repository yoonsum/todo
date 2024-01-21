package com.example.todoapp.service;

import com.example.todoapp.domain.LabelColor;
import com.example.todoapp.domain.Task;
import com.example.todoapp.dto.LabelDTO;
import com.example.todoapp.dto.MemberDTO;
import com.example.todoapp.dto.TaskDTO;
import com.example.todoapp.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class TaskServiceTest {
    @Autowired MemberService memberService;
    @Autowired LabelService labelService;
    @Autowired TaskService taskService;
    @Autowired TaskRepository taskRepository;
    private String member_id;
    private Long label_id;

    @BeforeEach
    public void beforeEach() {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setMemberId("user1");
        memberDTO.setMemberName("user1");
        memberDTO.setPassword("user1pw");
        member_id = memberService.join(memberDTO);

        LabelDTO labelDTO = new LabelDTO();
        labelDTO.setName("label1");
        labelDTO.setColor(LabelColor.LAVENDER);
        label_id = labelService.createLabel(labelDTO, member_id);
    }

    @Test
    public void 할일_생성() throws Exception{
        //given
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTitle("task1");
        taskDTO.setTaskDate(LocalDate.now());
        taskDTO.setLabel_id(label_id);

        //when
        Long id = taskService.addTask(taskDTO);

        //then
        Optional<Task> optionalTask = taskRepository.readTask(id);
        Task task;

        if(optionalTask.isPresent()){
            task = optionalTask.get();
            assertThat(task.getTitle()).isEqualTo(taskDTO.getTitle());
        }

    }
}