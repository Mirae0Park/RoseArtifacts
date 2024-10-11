package com.roseArtifacts.constant;

import lombok.Getter;

@Getter
public enum MemberRole {

    ADMIN(Authority.ADMIN),
    SUPERADMIN(Authority.SUPERADMIN);

    private final String authority;

    MemberRole(String authority){
        this.authority = authority;
    }

    public String getAuthority() {
        return this.authority;
    }

    public static class Authority{
        public static final String ADMIN = "ROLE_ADMIN";
        public static final String SUPERADMIN = "ROLE_SUPERADMIN";
    }
}
