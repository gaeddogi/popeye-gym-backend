package com.hy.popeyegym.security.provider;

import com.hy.popeyegym.web.domain.user.AuthProvider;
import com.hy.popeyegym.web.domain.user.Role;

import java.util.Map;

public class NaverOAuth2UserInfo implements OAuth2UserInfo{

    private Map<String, Object> attributes;

    public NaverOAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public AuthProvider getProvider() {
        return AuthProvider.naver;
    }

    @Override
    public String getProviderId() {
        return (String) attributes.get("id");
    }

    @Override
    public Role getRole() {
        return Role.ROLE_USER;
    }
}
