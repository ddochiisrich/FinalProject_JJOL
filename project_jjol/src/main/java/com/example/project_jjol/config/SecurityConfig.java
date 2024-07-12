package com.example.project_jjol.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import com.example.project_jjol.service.CustomOAuth2UserService;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    public SecurityConfig(CustomOAuth2UserService customOAuth2UserService) {
        this.customOAuth2UserService = customOAuth2UserService;
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authorizeRequests ->
                authorizeRequests
                    .anyRequest().permitAll()
            )
            .oauth2Login(oauth2Login -> {
                oauth2Login
                    .loginPage("/login")
                    .userInfoEndpoint(userInfoEndpoint ->
                        userInfoEndpoint.userService(customOAuth2UserService)
                    )
                    .defaultSuccessUrl("/lectures", true)
                    .failureHandler(customAuthenticationFailureHandler());
            })
        
	        .logout(logout -> {
	            logout
	                .logoutUrl("/logout")
	                .logoutSuccessUrl("/lectures")
	                .invalidateHttpSession(true)
	                .deleteCookies("JSESSIONID");
	        });

        return http.build();
    }

    @Bean
    public SimpleUrlAuthenticationFailureHandler customAuthenticationFailureHandler() {
        return new SimpleUrlAuthenticationFailureHandler("/register?error=true");
    }
}


