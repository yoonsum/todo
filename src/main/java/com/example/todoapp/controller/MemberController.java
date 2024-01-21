package com.example.todoapp.controller;

import com.example.todoapp.dto.MemberDTO;
import com.example.todoapp.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/members/create")
    public String createMemberForm(Model model){
        model.addAttribute("memberDTO", new MemberDTO());
        return "members/createMemberForm";
    }

    @PostMapping("/members/create")
    public String createMember(@Valid MemberDTO memberDTO, BindingResult result){
        if(result.hasErrors()){
            return "members/createMemberForm";
        }

        memberService.join(memberDTO);
        return "redirect:/";
    }
}
