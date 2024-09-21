package com.example.project_jjol.service;

import com.example.project_jjol.model.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserService userService;
    private final HttpSession session;

    @Autowired
    public CustomOAuth2UserService(UserService userService, HttpSession session) {
        this.userService = userService;
        this.session = session;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String email = extractEmail(oAuth2User, userRequest);
        String name = extractName(oAuth2User, userRequest);
        String provider = userRequest.getClientRegistration().getRegistrationId();

        if (email == null) {
            throw new OAuth2AuthenticationException("이메일을 가져올 수 없습니다.");
        }

        User user = userService.findByEmailAndProvider(email, provider);
        if (user == null) {
            storeSocialUserInfoInSession(email, name, provider);
            throw new OAuth2AuthenticationException("회원가입을 진행해주세요.");
        }

        session.setAttribute("loggedInUser", user);

        Map<String, Object> attributes = Collections.singletonMap("email", email);

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                attributes,
                "email");
    }

    private String extractEmail(OAuth2User oAuth2User, OAuth2UserRequest userRequest) {
        String provider = userRequest.getClientRegistration().getRegistrationId();
        if ("naver".equals(provider)) {
            Map<String, Object> response = (Map<String, Object>) oAuth2User.getAttributes().get("response");
            return (String) response.get("email");
        } else if ("kakao".equals(provider)) {
            Map<String, Object> kakaoAccount = (Map<String, Object>) oAuth2User.getAttributes().get("kakao_account");
            if (kakaoAccount != null) {
                return (String) kakaoAccount.get("email");
            }
        }
        return oAuth2User.getAttribute("email");
    }

    private String extractName(OAuth2User oAuth2User, OAuth2UserRequest userRequest) {
        String provider = userRequest.getClientRegistration().getRegistrationId();
        if ("naver".equals(provider)) {
            Map<String, Object> response = (Map<String, Object>) oAuth2User.getAttributes().get("response");
            return (String) response.get("name");
        } else if ("kakao".equals(provider)) {
            Map<String, Object> kakaoAccount = (Map<String, Object>) oAuth2User.getAttributes().get("kakao_account");
            if (kakaoAccount != null) {
                Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
                if (profile != null) {
                    return (String) profile.get("nickname");
                }
            }
        }
        return oAuth2User.getAttribute("name");
    }

    private void storeSocialUserInfoInSession(String email, String name, String provider) {
        session.setAttribute("socialEmail", email);
        session.setAttribute("socialName", name);
        session.setAttribute("socialProvider", provider);
    }
}
