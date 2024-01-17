package com.example.todoapp.service;

import com.example.todoapp.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class MemberServiceTest {
    @Autowired MemberService memberService;

    @Test
    public void 회원가입() throws Exception{
        //given
        Member member = new Member("user1", "user1", "user1pw");

        //when
        String id = memberService.join(member);

        //then
        assertThat(id).isEqualTo(member.getId());
    }

    @Test
    public void 중복_회원_예외() throws Exception{
        //given
        Member member1 = new Member("user1", "user1", "user1pw");
        Member member2 = new Member("user1", "user1", "user1pw");

        //when
        String id1 = memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class,
                ()->memberService.join(member2));

        //then
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }

    @Test
    public void 로그인() throws Exception{
        //given
        Member member1 = new Member("user1", "user1", "user1pw");

        //when
        String login_id = memberService.login(member1.getId(), member1.getPassword());

        assertThat(login_id).isEqualTo(member1.getId());
    }

    @Test
    public void 존재하지_않는_회원() throws Exception{
        //given
        Member member1 = new Member("user1", "user1", "user1pw");
        memberService.join(member1);

        Member login_member = new Member("user", "user1", "user1pw");

        //when
        IllegalStateException e = assertThrows(IllegalStateException.class,
                ()->memberService.login(login_member.getId(), login_member.getPassword()));

        //then
        assertThat(e.getMessage()).isEqualTo("존재하지 않는 회원입니다.");
    }

    @Test
    public void 로그인_실패() throws Exception{
        //given
        Member member1 = new Member("user1", "user1", "user1pw");
        memberService.join(member1);

        Member login_member = new Member("user1", "user1", "pw");

        //when
        IllegalStateException e = assertThrows(IllegalStateException.class,
                ()->memberService.login(login_member.getId(), login_member.getPassword()));

        //then
        assertThat(e.getMessage()).isEqualTo("로그인 실패. 아이디 또는 비밀번호가 일치하지 않습니다.");
    }
}