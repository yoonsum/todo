package com.example.todoapp.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Member {
    @Id
    @Column(name = "member_id")
    private String id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "label_id")
    private List<Label> labels = new ArrayList<>();

    @Builder
    public Member(String id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public void updateMember(String name, String password){
        if(name != null){
            this.name = name;
        }
        if(password != null){
            this.password = password;
        }
    }
}