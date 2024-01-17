package com.example.todoapp.service;

import com.example.todoapp.domain.Member;
import com.example.todoapp.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class MemberService {
    @Autowired private MemberRepository memberRepository;

    /**
     * 회원가입
     * @param member
     * @return
     */
    @Transactional
    public String join(Member member){
        Optional<Member> optionalMember = memberRepository.findById(member.getId());
        if(optionalMember.isPresent()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }

        memberRepository.save(member);
        return member.getId();
    }

    /**
     * 로그인
     * @param id
     * @param password
     * @return
     */
    public String login(String id, String password){
        Optional<Member> optionalMember = memberRepository.findById(id);

        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            if (member.getPassword().equals(password)) {
                return id;
            } else {
                throw new IllegalStateException("로그인 실패. 아이디 또는 비밀번호가 일치하지 않습니다.");
            }
        } else {
            throw new IllegalStateException("존재하지 않는 회원입니다.");
        }
    }


    /**
     * 회원탈퇴
     * @param id
     */
    @Transactional
    public void deleteMember(String id){
        memberRepository.deleteMember(id);
    }

}
