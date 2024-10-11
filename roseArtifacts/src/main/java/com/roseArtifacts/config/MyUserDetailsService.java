package com.roseArtifacts.config;

import com.roseArtifacts.entity.Member;
import com.roseArtifacts.repository.MemberRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final HttpSession session;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member member = memberRepository.findWithRoseSetByUserId(username);

        if (member != null) {
            /** 시큐리티 세션에 유저 정보 저장**/
            return new UserAdapter(member);
        } else {
            throw new UsernameNotFoundException("사용자가 존재하지 않습니다.");
        }
    }
}
