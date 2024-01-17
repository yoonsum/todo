package com.example.todoapp.domain;

import jakarta.persistence.*;
import lombok.Getter;

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
}
