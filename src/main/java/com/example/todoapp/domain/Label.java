package com.example.todoapp.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.ArrayList;

@Entity
@Getter
public class Label {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="label_id")
    private Long id;

    @Column(name="label_name")
    private String name;

    @Enumerated(EnumType.STRING)
    private LabelColor color;

    @Enumerated(EnumType.STRING)
    private SortBy sortBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "task_id")
    private List<Task> tasks = new ArrayList<>();

    @Builder
    public Label(Long id, String name, LabelColor color, Member member) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.sortBy = SortBy.MYSORT;
        this.member = member;
    }
}
