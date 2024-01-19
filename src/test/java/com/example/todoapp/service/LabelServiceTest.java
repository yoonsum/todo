package com.example.todoapp.service;

import com.example.todoapp.domain.LabelColor;
import com.example.todoapp.dto.LabelDTO;
import com.example.todoapp.dto.MemberDTO;
import com.example.todoapp.repository.LabelRepository;
import com.example.todoapp.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class LabelServiceTest {
    @Autowired LabelRepository labelRepository;
    @Autowired LabelService labelService;
    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    private String member_id;

    @BeforeEach
    public void beforeEach() {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setMemberId("user1");
        memberDTO.setMemberName("user1");
        memberDTO.setPassword("user1pw");
        member_id = memberService.join(memberDTO);
    }

    @Test
    @Transactional
    @Rollback(false)
    public void 라벨_추가하기() throws Exception{
        //given
        LabelDTO labelDTO = new LabelDTO();
        labelDTO.setName("label1");
        labelDTO.setColor(LabelColor.LAVENDER);

        //when
        labelService.createLabel(labelDTO, member_id);

    }
}