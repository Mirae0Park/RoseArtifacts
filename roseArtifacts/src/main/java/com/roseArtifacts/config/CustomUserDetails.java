package com.roseArtifacts.config;

import com.roseArtifacts.entity.Member;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Getter
@Setter
public class CustomUserDetails implements UserDetails {

    private Member member;

    /* 일반 로그인 생성자 */
    public CustomUserDetails(Member member){
        this.member = member;
    }

    public void setUsername(String newUserId) {
        this.member.setUserId(newUserId);
    }

    public void setPassword(String newPw) {
        this.member.setPw(newPw);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return member.getMemberRole().getAuthority();
            }
        });
        return collect;

    }

    @Override
    public String getPassword() {
        return member.getPw();
    }

    @Override
    public String getUsername() {
        return member.getUserId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
