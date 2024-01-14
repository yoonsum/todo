package com.example.todoapp.domain;

import jakarta.persistence.*;

@Entity
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="tag_id")
    private Long id;

    @Column(name="tag_name")
    private String name;

    @Enumerated(EnumType.STRING)
    private TagColor color;

    @Enumerated(EnumType.STRING)
    private SortBy sortBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;
}
