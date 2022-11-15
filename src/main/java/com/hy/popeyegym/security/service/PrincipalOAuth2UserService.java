package com.hy.popeyegym.security.service;

import com.hy.popeyegym.domain.user.User;
import com.hy.popeyegym.exception.CustomException;
import com.hy.popeyegym.exception.exceptionType.AuthExceptionType;
import com.hy.popeyegym.exception.exceptionType.UserExceptionType;
import com.hy.popeyegym.repository.user.UserRepository;
import com.hy.popeyegym.security.PrincipalDetails;
import com.hy.popeyegym.security.provider.GoogleOAuth2UserInfo;
import com.hy.popeyegym.security.provider.KakaoOAuth2UserInfo;
import com.hy.popeyegym.security.provider.NaverOAuth2UserInfo;
import com.hy.popeyegym.security.provider.OAuth2UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.naming.AuthenticationException;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrincipalOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest); // 사용자 정보 받아옴.

        log.info("========PrincipalOAuth2UserService");
//        try {
            return processOAuth2User(oAuth2UserRequest, oAuth2User);
//        } catch (AuthenticationException ex) {
//            throw ex;
//        } catch (Exception ex) {
            // Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler
//            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
//        }
    }

    public PrincipalDetails loadUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new CustomException(UserExceptionType.NOT_FOUND_USER)
        );
        return PrincipalDetails.create(user);
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {

        // 사용자 정보를 담는다.
        OAuth2UserInfo oAuth2UserInfo = null;

        String registrationId = oAuth2UserRequest.getClientRegistration().getRegistrationId();
        log.info("registrationId: {}", registrationId);
        if (registrationId.equals("google")) {
            oAuth2UserInfo = new GoogleOAuth2UserInfo(oAuth2User.getAttributes());
        } else if (registrationId.equals("naver")) {
            oAuth2UserInfo = new NaverOAuth2UserInfo((Map) oAuth2User.getAttributes().get("response"));
        } else if (registrationId.equals("kakao")) {
            oAuth2UserInfo = new KakaoOAuth2UserInfo((Map) oAuth2User.getAttributes());
        }

        // 필수값인 이메일이 없다면 익셉션 던짐
        if(!StringUtils.hasText(oAuth2UserInfo.getEmail())) {
            throw new CustomException(AuthExceptionType.AUTH_EXCEPTION);
//            throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
        }

        // DB에서 email로 조회
        Optional<User> userOptional = userRepository.findByEmail(oAuth2UserInfo.getEmail());
        User user;
        if(userOptional.isPresent()) { // 회원 가입 된 사용자
            user = userOptional.get();

            if(!user.getProvider().name().equals(registrationId)) {
                // 같은 이메일, 다른 제공자일 때 가입 불가능 익셉션 던짐.
                throw new CustomException(AuthExceptionType.AUTH_EXCEPTION);
//                throw new OAuth2AuthenticationProcessingException("Looks like you're signed up with " +
//                        user.getProvider() + " account. Please use your " + user.getProvider() +
//                        " account to login.");
            }
//            user = updateUser(user, oAuth2UserInfo);
            user.update(oAuth2UserInfo.getEmail(), oAuth2UserInfo.getName()); // @Transactional 붙여아 하는 거 같은데
        } else {
            // 신규 사용자
            user = saveUser(oAuth2UserInfo);
        }
        return PrincipalDetails.create(user);
    }
    private User saveUser(OAuth2UserInfo oAuth2UserInfo) {
        User user = User.create(
                oAuth2UserInfo.getEmail(),
                "sss",
                oAuth2UserInfo.getName(),
                oAuth2UserInfo.getProvider(),
                oAuth2UserInfo.getProviderId(),
                oAuth2UserInfo.getRole()
        );

        return userRepository.save(user);
    }

    // jpa 더티 체킹으로 없어도 될 거 같음.
//    private User updateUser(User existingUser, OAuth2UserInfo oAuth2UserInfo) {
//        existingUser.setName(oAuth2UserInfo.getName());
//        existingUser.setImageUrl(oAuth2UserInfo.getImageUrl());
//        return userRepository.save(existingUser);
//    }

}


