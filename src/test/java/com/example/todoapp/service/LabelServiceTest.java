package com.example.todoapp.service;

import com.example.todoapp.domain.Label;
import com.example.todoapp.domain.LabelColor;
import com.example.todoapp.domain.SortBy;
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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class LabelServiceTest {
    @Autowired LabelService labelService;
    @Autowired MemberService memberService;
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
    public void 라벨_추가하기() throws Exception{
        //given
        LabelDTO labelDTO = new LabelDTO();
        labelDTO.setName("label1");
        labelDTO.setColor(LabelColor.LAVENDER);

        //when
        Long id = labelService.createLabel(labelDTO, member_id);

        //then
        Label label = labelService.readLabel(id);
        assertThat(label.getId()).isEqualTo(id);
    }

    @Test
    public void 라벨_수정하기() throws Exception{
        //given
        LabelDTO labelDTO = new LabelDTO();
        labelDTO.setName("label1");
        labelDTO.setColor(LabelColor.LAVENDER);

        Long id = labelService.createLabel(labelDTO, member_id);

        //when
        labelDTO.setColor(LabelColor.LIGHTSKYBLUE);

        labelService.updateLabel(id, labelDTO);

        //then
        Label label = labelService.readLabel(id);
        assertThat(label.getColor()).isEqualTo(labelDTO.getColor());
    }

    @Test
    public void 라벨_정렬기준_변경() throws Exception{
        //given
        LabelDTO labelDTO = new LabelDTO();
        labelDTO.setName("label1");
        labelDTO.setColor(LabelColor.LAVENDER);

        Long id = labelService.createLabel(labelDTO, member_id);

        //when
        labelService.updateSortBy(id, SortBy.PRIORITY);

        //then
        Label label = labelService.readLabel(id);
        assertThat(label.getSortBy()).isEqualTo(SortBy.PRIORITY);
    }

    @Test
    public void 라벨_삭제() throws Exception{
        //given
        LabelDTO labelDTO = new LabelDTO();
        labelDTO.setName("label1");
        labelDTO.setColor(LabelColor.LAVENDER);

        Long id = labelService.createLabel(labelDTO, member_id);

        //when
        labelService.deleteLabel(id);

        //then
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                ()->labelService.readLabel(id));
        assertThat(e.getMessage()).isEqualTo("존재하지 않는 Label입니다.");
    }
}