package com.example.todoapp.repository;

import com.example.todoapp.domain.Member;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepository {
    private final EntityManager em;

    public void save(Member member){
        em.persist(member);
    }

    public Optional<Member> findById(String id){
        return Optional.ofNullable(em.find(Member.class, id));
    }

    public void update(Member origin, Member update){
        origin.updateMember(update.getName(), update.getPassword());
    }

    public void delete(String id){
        Member deleteMember = em.find(Member.class, id);
        em.remove(deleteMember);
    }
}
