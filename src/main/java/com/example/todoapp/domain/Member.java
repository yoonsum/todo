package com.example.todoapp.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

public class Member {
    @Id
    @Column(name="member_id")
    private Long id;

    @NotNull
    private String name;

    private String password;

    @NotNull
    private String email;

    @NotNull
    private String phone;
}
