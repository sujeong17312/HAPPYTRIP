package com.HAPPYTRIP.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberForm {

    @NotBlank(message = "아이디를 입력하세요.")
    private String id;

    @NotBlank(message = "비밀번호를 입력하세요.")
    private String password;

    @NotBlank(message = "비밀번호 중복 확인을 하세요.")
    private String checkpassword;

    @NotBlank(message = "이름을 입력하세요.")
    private String name;

    @NotBlank(message = "전화번호를 입력하세요.")
    private String phone;

    @NotNull
    private String birthday;
}
