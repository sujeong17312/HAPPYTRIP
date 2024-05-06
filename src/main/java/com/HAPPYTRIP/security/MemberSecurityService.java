package com.HAPPYTRIP.security;

import com.HAPPYTRIP.domain.Member;
import com.HAPPYTRIP.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberSecurityService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Optional<Member> memberOptional = memberRepository.findByUserId(userId);
        if(memberOptional.isEmpty()){
            throw new UsernameNotFoundException("사용자를 찾을수 없습니다.");
        }

        Member member = memberOptional.get();
        List<GrantedAuthority> authorities = new ArrayList<>();
        if("admin".equals(userId)){
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }else{
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }
        return new User(member.getUserId(), member.getPassword(), authorities);
    }

}
