package com.hy.popeyegym.security.provider;

import com.hy.popeyegym.domain.user.Role;
import com.hy.popeyegym.domain.user.AuthProvider;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class GoogleOAuth2UserInfo implements OAuth2UserInfo {

    private final Map<String, Object> attributes;

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
        return AuthProvider.google;
    }

    @Override
    public String getProviderId() {
        return (String) attributes.get("sub");
    }

    @Override
    public Role getRole() {
        return Role.ROLE_USER;
    }
}
