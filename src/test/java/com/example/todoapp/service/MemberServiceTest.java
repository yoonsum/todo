package com.example.todoapp.service;

import com.example.todoapp.domain.Member;
import com.example.todoapp.dto.MemberDTO;
import com.example.todoapp.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class MemberServiceTest {
    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    public void 회원가입() throws Exception{
        //given
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setMemberId("user1");
        memberDTO.setMemberName("user1");
        memberDTO.setPassword("user1pw");

        //when
        String id = memberService.join(memberDTO);

        //then
        assertThat(id).isEqualTo(memberDTO.getMemberId());
    }

    @Test
    public void 중복_회원_예외() throws Exception{
        //given
        MemberDTO member1 = new MemberDTO();
        member1.setMemberId("user1");
        member1.setMemberName("user1");
        member1.setPassword("user1pw");
        String id1 = memberService.join(member1);

        MemberDTO member2 = new MemberDTO();
        member2.setMemberId("user1");
        member2.setMemberName("user1");
        member2.setPassword("user1pw");

        //when
        IllegalStateException e = assertThrows(IllegalStateException.class,
                ()->memberService.join(member2));

        //then
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }

    @Test
    public void 로그인() throws Exception{
        //given
        MemberDTO member1 = new MemberDTO();
        member1.setMemberId("user1");
        member1.setMemberName("user1");
        member1.setPassword("user1pw");
        String memberId = memberService.join(member1);

        //when
        String login_id = memberService.login(member1.getMemberId(), member1.getPassword());

        assertThat(login_id).isEqualTo(memberId);
    }

    @Test
    public void 존재하지_않는_회원() throws Exception{
        //given
        MemberDTO member1 = new MemberDTO();
        member1.setMemberId("user1");
        member1.setMemberName("user1");
        member1.setPassword("user1pw");
        memberService.join(member1);

        //when
        Member login_member = Member.builder()
                .id("user")
                .name("user1")
                .password("user1pw")
                .build();

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                ()->memberService.login(login_member.getId(), login_member.getPassword()));

        //then
        assertThat(e.getMessage()).isEqualTo("존재하지 않는 회원입니다.");
    }

    @Test
    public void 로그인_실패() throws Exception{
        //given
        MemberDTO member1 = new MemberDTO();
        member1.setMemberId("user1");
        member1.setMemberName("user1");
        member1.setPassword("user1pw");
        memberService.join(member1);

        //when
        Member login_member = Member.builder()
                .id("user1")
                .name("user1")
                .password("pw")
                .build();

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                ()->memberService.login(login_member.getId(), login_member.getPassword()));

        //then
        assertThat(e.getMessage()).isEqualTo("로그인 실패. 아이디 또는 비밀번호가 일치하지 않습니다.");
    }

    @Test
    @Rollback(value = false)
    public void 회원_정보_수정() throws Exception{
        //given
        MemberDTO member1 = new MemberDTO();
        member1.setMemberId("user1");
        member1.setMemberName("user1");
        member1.setPassword("user1pw");
        memberService.join(member1);

        //when
        MemberDTO updateMember = new MemberDTO();
        updateMember.setMemberId("user1");
        updateMember.setMemberName("userUpdate");
        updateMember.setPassword("userUpdatePW");

        memberService.updateMember(updateMember);

        //then
        Optional<Member> optionalMember = memberRepository.findById(updateMember.getMemberId());
        if(optionalMember.isPresent()){
            Member member = optionalMember.get();
            assertThat(member.getName()).isEqualTo(updateMember.getMemberName());
            assertThat(member.getPassword()).isEqualTo(updateMember.getPassword());
        }

    }
}