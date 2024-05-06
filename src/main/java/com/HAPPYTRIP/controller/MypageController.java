package com.HAPPYTRIP.controller;

import com.HAPPYTRIP.domain.Member;
import com.HAPPYTRIP.domain.Reservation;
import com.HAPPYTRIP.service.MemberService;
import com.HAPPYTRIP.service.PaymentService;
import com.HAPPYTRIP.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/mypage")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ROLE_USER')")
public class MypageController {

    private final MemberService memberService;
    private final ReservationService reservationService;
    private final PaymentService paymentService;

    //마이페이지
    @GetMapping("")
    public String mypage() {
        return "/mypage/mypage";
    }

    //회원정보 수정
    @GetMapping("/update")
    public String updateForm(Model model, Principal principal) {

        String username = principal.getName();
        Optional<Member> optionalMember = memberService.findByUserId(username);
        optionalMember.ifPresent(member -> model.addAttribute("member", member));
        return "/mypage/updateForm";
    }

    @PostMapping("/update")
    public String updatePassword(@RequestParam("name") String name,
                                 @RequestParam("phone") String phone,
                                 @RequestParam("password") String password,
                                 Principal principal) {
        String username = principal.getName();
        Optional<Member> optionalMember = memberService.findByUserId(username);
        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            member.setName(name);
            member.setPhone(phone);
            member.setPassword(password);
            memberService.update(member);
        }

        return "/mypage/mypage";
    }

    //회원 탈퇴
    @GetMapping("/deleteCheck")
    public String deleteCheck() {
        return "/mypage/deleteCheck";
    }
    @PostMapping("/delete")
    public String deleteMember(String password, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        // 비밀번호 확인
        if (memberService.deleteCheck(userId, password)) {
            memberService.deleteByUserId(userId);
            return "redirect:/member/logout";
        } else {
            model.addAttribute("error", true);
            return "/mypage/deleteCheck";
        }
    }

    //예약 조회
    @GetMapping("/reservation")
    public String myReservation(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        String username = userDetails.getUsername();
        Optional<Member> memberOptional = memberService.findByUserId(username);

        if (memberOptional.isEmpty()) {
            throw new RuntimeException("회원을 찾을 수 없습니다.");
        }

        Member member = memberOptional.get();
        List<Reservation> reservationList = reservationService.getReservationsByMemberId(member.getId());

        model.addAttribute("reservationList", reservationList);
        return "/mypage/myReservation";
    }

    //예약 취소
    @PostMapping("/cancelReservation/{id}")
    public String cancelReservation(@PathVariable("id") Long id) {
        reservationService.cancelReservation(id);
        paymentService.cancelPayment(id);
        return "/mypage/myReservation";
    }

}