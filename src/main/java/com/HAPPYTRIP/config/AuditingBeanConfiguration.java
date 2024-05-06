package com.HAPPYTRIP.config;

import com.HAPPYTRIP.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class AuditingBeanConfiguration {

    private final MemberService memberService;

    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                return Optional.empty();
            }

            // 인증 정보에서 사용자 이름 또는 ID를 가져옵니다.
            String username = authentication.getName();
            return Optional.of(username);
        };
    }
}
