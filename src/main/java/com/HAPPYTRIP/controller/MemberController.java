package com.HAPPYTRIP.controller;

import com.HAPPYTRIP.domain.UserRole;
import com.HAPPYTRIP.dto.MemberForm;
import com.HAPPYTRIP.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    //회원가입
    @GetMapping("/join")
    public String JoinForm(MemberForm memberForm) {
        return "join";
    }

    @PostMapping("/join")
    public String join(@Valid MemberForm memberForm, BindingResult bindingResult) {

        //비밀번호가 일치하지 않을 경우
        if(!memberForm.getPassword().equals(memberForm.getCheckpassword())) {
            bindingResult.rejectValue("checkpassword", "NotPassword", "비밀번호가 일치하지 않습니다.");
        }
        //중복 아이디 검사
        if (memberService.isIdAlreadyExist(memberForm.getId())) {
            bindingResult.reject("joinFailed", "이미 등록된 아이디입니다.");
            return "join";
        }

        if (bindingResult.hasErrors()) {
            return "join";
        }

        try {
            System.out.println(memberForm.getId());
            memberService.create(memberForm.getId(), memberForm.getPassword(), memberForm.getName(),
                    memberForm.getPhone(), memberForm.getBirthday(), UserRole.USER);
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("joinFailed", "이미 등록된 사용자입니다.");
            return "join";
        } catch (Exception e) {
            e.printStackTrace();
            bindingResult.reject("joinFailed", e.getMessage());
            return "join";
        }

        return "redirect:/member/login";
    }

    //중복확인
    @GetMapping("/idCheck/{id}")
    public ResponseEntity<String> idCheck(@PathVariable String id) {
        if (memberService.isIdAlreadyExist(id)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("중복된 아이디입니다.");
        } else {
            return ResponseEntity.ok().body("사용 가능한 아이디입니다.");
        }
    }

}