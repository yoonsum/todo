package com.example.todoapp.repository;

import com.example.todoapp.domain.Label;
import com.example.todoapp.domain.LabelColor;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class LabelRepository {
    private final EntityManager em;

    public void saveLabel(Label label){
        em.persist(label);
    }

    public void updateLabel(Label origin, String name, LabelColor color){
        origin.updateLabel(name, color);
    }

    public void updateSortBy(){
        //1.sortBy 값변경
        //2.List<task>의 정렬변경
        //task entity 에 만들것이냐...어디에 만들어야하지...
    }

    public Optional<Label> findByOne(Long label_id){
        return Optional.ofNullable(em.find(Label.class, label_id));
    }

    public List<Label> findByMember(String member_id){
        return em.createQuery("select l from Label m where l.member_id = :member_id", Label.class)
                .setParameter("member_id", member_id)
                .getResultList();
    }

    public void deleteLabel(Long label_id){
        Label label = em.find(Label.class, label_id);
        em.remove(label);
    }
}
