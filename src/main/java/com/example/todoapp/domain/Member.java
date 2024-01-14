package com.example.todoapp.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Entity
@Getter
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
