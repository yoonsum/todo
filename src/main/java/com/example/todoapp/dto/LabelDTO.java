package com.example.todoapp.dto;

import com.example.todoapp.domain.LabelColor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LabelDTO {

    @NotBlank(message = "이름은 빈칸일 수 없습니다.")
    private String name;

    @NotEmpty(message = "색상은 빈칸일 수 없습니다.")
    private LabelColor color;
}
