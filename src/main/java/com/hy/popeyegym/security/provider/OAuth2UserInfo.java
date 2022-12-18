package com.hy.popeyegym.security.provider;

import com.hy.popeyegym.web.domain.user.AuthProvider;
import com.hy.popeyegym.web.domain.user.Role;

public interface OAuth2UserInfo {

    String getName();
    String getEmail();
    AuthProvider getProvider();
    String getProviderId();
    Role getRole();
}
