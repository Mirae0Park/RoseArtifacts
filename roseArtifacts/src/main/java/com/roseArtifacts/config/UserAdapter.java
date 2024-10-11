package com.roseArtifacts.config;


import com.roseArtifacts.entity.Member;
import lombok.Getter;

@Getter
public class UserAdapter extends CustomUserDetails{

    private Member member;

    public UserAdapter(Member member){
        super(member);
        this.member = member;
    }

}
