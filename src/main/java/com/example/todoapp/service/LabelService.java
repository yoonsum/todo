package com.example.todoapp.service;

import com.example.todoapp.domain.Label;
import com.example.todoapp.domain.Member;
import com.example.todoapp.domain.SortBy;
import com.example.todoapp.dto.LabelDTO;
import com.example.todoapp.repository.LabelRepository;
import com.example.todoapp.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class LabelService {
    @Autowired private LabelRepository labelRepository;
    @Autowired private MemberRepository memberRepository;

    /**
     * 라벨 생성하기
     * @param labelDTO
     * @param member_id
     */
    @Transactional
    public Long createLabel(LabelDTO labelDTO, String member_id){
        Optional<Member> optionalMember = memberRepository.findById(member_id);
        if(optionalMember.isPresent()){
            Member member = optionalMember.get();

            Label label = Label.builder()
                    .name(labelDTO.getName())
                    .color(labelDTO.getColor())
                    .member(member)
                    .build();

            return labelRepository.saveLabel(label);
        }

        return null;
    }

    /**
     * 라벨 수정하기
     * @param label_id
     * @param labelDTO
     */
    @Transactional
    public void updateLabel(Long label_id, LabelDTO labelDTO){
        Label label = validateExistence(label_id);
        labelRepository.updateLabel(label, labelDTO.getName(), labelDTO.getColor());
    }

    /**
     * 라벨 정렬기준 수정하기
     * @param label_id
     * @param sortBy
     */
    @Transactional
    public void updateSortBy(Long label_id, SortBy sortBy){
        //1. 정렬기준 변경
        Label label = validateExistence(label_id);
        labelRepository.updateSortBy(label, sortBy);

        //2. 바뀐 정렬기준으로 task 정렬 후 다시 불러오기?? -> 동적 가능??????
    }

    /**
     * 라벨 정보 조회
     * @param label_id
     */
    public Label readLabel(Long label_id){
        return validateExistence(label_id);
    }

    /**
     * member_id가 생성한 label 조회
     * @param member_id
     * @return
     */
    public List<Label> readLabelByMember(String member_id){
        return labelRepository.findByMember(member_id);
    }

    @Transactional
    public void deleteLabel(Long label_id){
        validateExistence(label_id);
        labelRepository.deleteLabel(label_id);
    }

    /**
     * 라벨 존재 유뮤 검증
     * @param label_id
     * @return
     */
    private Label validateExistence(Long label_id){
        Optional<Label> optionalLabel = labelRepository.findByOne(label_id);
        if(optionalLabel.isEmpty()){
            throw new IllegalArgumentException("존재하지 않는 Label입니다.");
        }
        return optionalLabel.get();
    }
}
