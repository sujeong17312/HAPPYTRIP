package com.HAPPYTRIP.service;

import com.HAPPYTRIP.domain.Member;
import com.HAPPYTRIP.domain.UserRole;
import com.HAPPYTRIP.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class MemberService {


    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;


    public List<Member> getList() {
        return memberRepository.findAll();
    }

    public Page<Member> getList(int page, String kw) {
        Pageable pageable = PageRequest.of(page, 10);
        return memberRepository.findAllByKeyword(kw, pageable);
    }

    public Member getMember(String userId) {
        Optional<Member> optionalMember=memberRepository.findByUserId(userId);
        if(optionalMember.isPresent()){
            return optionalMember.get();
        }else{
            throw new RuntimeException("Member not found");
        }
    }

    //회원생성
    public Member create(String userId, String password, String name, String phone, String birthday, UserRole role) {
        Member member = new Member();
        member.setUserId(userId);
        member.setPassword(passwordEncoder.encode(password));
        member.setName(name);
        member.setPhone(phone);
        member.setBirthday(birthday);
        member.setRole(role);
        memberRepository.save(member);
        return member;
    }

    //회원정보 수정
    public Member update(Member member) {
        Member persistence = memberRepository.findByUserId(member.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));

        persistence.setName(member.getName());
        persistence.setPhone(member.getPhone());

        if (member.getPassword() != null && !member.getPassword().isEmpty()) {
            String encodedPassword = passwordEncoder.encode(member.getPassword());
            persistence.setPassword(encodedPassword);
        }

        return memberRepository.save(persistence);
    }

    public Optional<Member> findByUserId(String userId) {
        return memberRepository.findByUserId(userId);
    }


    //회원탈퇴
    public void deleteByUserId(String userId) {
        memberRepository.deleteByUserId(userId);
    }

    public boolean deleteCheck(String userId, String password) {
        Optional<Member> optionalMember = memberRepository.findByUserId(userId);
        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            return passwordEncoder.matches(password, member.getPassword());
        } else {
            return false;
        }
    }

    //아이디 중복 확인
    public boolean isIdAlreadyExist(String id) {
        Optional<Member> existingMember = memberRepository.findByUserId(id);
        return existingMember.isPresent();
    }
}