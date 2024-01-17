package com.example.todoapp.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class MemberDTO {
    @Pattern(regexp = "^[a-z]+$|^[a-z][0-9]+$", message = "ID는 알파벳 소문자와 숫자만 가능합니다.")
    @Size(min=7, max=12, message = "ID는 7~12자리입니다.")
    private String memberId;

    @Pattern(regexp = "^[a-zA-Z]*[a-zA-Z0-9]*[a-zA-Z]*$", message = "name은 알파벳과 숫자의 조합이어야 합니다.")
    @Size(min=3, max=10, message = "name은 3~10자리입니다.")
    private String memberName;

    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*()-_+=])[A-Za-z\\d!@#$%^&*()-_+=]+$", message = "비밀번호는 알파벳 대문자, 소문자, 숫자, 특수 기호를 모두 포함해야 합니다.")
    @Size(min=8, max=16, message = "passwordsms 8~16자리입니다.")
    private String password;
}
