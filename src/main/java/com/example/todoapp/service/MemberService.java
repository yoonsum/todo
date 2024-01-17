package com.example.todoapp.service;

import com.example.todoapp.domain.Member;
import com.example.todoapp.dto.MemberDTO;
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
     * @param memberDTO
     * @return
     */
    @Transactional
    public String join(MemberDTO memberDTO){
        validationDuplication(memberDTO.getMemberId());

        Member member = Member.builder()
                .id(memberDTO.getMemberId())
                .name(memberDTO.getMemberName())
                .password(memberDTO.getPassword())
                .build();

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
        Member member = validateExistence(id);

        if (member.getPassword().equals(password)) {
            return id;
        } else {
            throw new IllegalArgumentException("로그인 실패. 아이디 또는 비밀번호가 일치하지 않습니다.");
        }
    }

    /**
     * 회원 정보 수정
     * @param member
     */
    @Transactional
    public void updateMember(MemberDTO member){
        Member origin = validateExistence(member.getMemberId());

        Member update = Member.builder()
                .id(member.getMemberId())
                .name(member.getMemberName())
                .password(member.getPassword())
                .build();

        memberRepository.update(origin, update);
    }

    /**
     * 회원탈퇴
     * @param id
     */
    @Transactional
    public void deleteMember(String id){
        memberRepository.delete(id);
    }


    /**
     * 회원 중복 검사
     * @param memberId
     */
    private void validationDuplication(String memberId){
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        if(optionalMember.isPresent()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**
     * 회원 존재하는지 검사
     * @param memberId
     * @return
     */
    private Member validateExistence(String memberId) {
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        if (optionalMember.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 회원입니다.");
        }

        return optionalMember.get();
    }
}
