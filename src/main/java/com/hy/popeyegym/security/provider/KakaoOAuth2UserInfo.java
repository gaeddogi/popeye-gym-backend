package com.hy.popeyegym.security.provider;

import com.hy.popeyegym.web.domain.user.AuthProvider;
import com.hy.popeyegym.web.domain.user.Role;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Map;

@RequiredArgsConstructor
@ToString
public class KakaoOAuth2UserInfo implements OAuth2UserInfo {

    private final Map<String, Object> attributes;

    @Override
    public String getName() {
        return (String) ((Map) attributes.get("properties")).get("nickname");
    }

    @Override
    public String getEmail() {
        return (String) ((Map) attributes.get("kakao_account")).get("email");
    }

    @Override
    public AuthProvider getProvider() {
        return AuthProvider.kakao;
    }

    @Override
    public String getProviderId() {
        return Long.toString((Long) attributes.get("id"));
    }

    @Override
    public Role getRole() {
        return Role.ROLE_USER;
    }
}
