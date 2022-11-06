package com.hy.popeyegym.security.provider;

import com.hy.popeyegym.domain.user.Role;
import com.hy.popeyegym.domain.user.AuthProvider;

public interface OAuth2UserInfo {

    String getName();
    String getEmail();
    AuthProvider getProvider();
    String getProviderId();
    Role getRole();
}
