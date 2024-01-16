package com.wss.websoso.config;

import com.wss.websoso.config.jwt.CustomAccessDeniedHandler;
import com.wss.websoso.config.jwt.CustomJwtAuthenticationEntryPoint;
import com.wss.websoso.config.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@RequiredArgsConstructor
@EnableWebSecurity //web Security를 사용할 수 있게
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomJwtAuthenticationEntryPoint customJwtAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            /*
            http의 특정 보안 구성을 비활성화하고, 인증 인가에 대한 예외를 처리
             */
        http.csrf(AbstractHttpConfigurer::disable) // csrf 공격을 대비하기 위한 csrf 토큰 disable 하기
                .formLogin(AbstractHttpConfigurer::disable) // form login 비활성화 jwt를 사용하고 있으므로 폼 기반 로그인은 필요하지 않다.
                .httpBasic(
                        AbstractHttpConfigurer::disable)// http 기본 인증은 사용자 이름과 비밀번호를 평문으로 전송하기 때문에 보안적으로 취약, 기본 인증을 비활성화 하고 있음
                .sessionManagement(session -> {
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                }) // session은 사용하지 않으므로 세션 관리를 STATELESS로 설정
                .exceptionHandling(exception ->
                {
                    exception.authenticationEntryPoint(customJwtAuthenticationEntryPoint); // 인증 에러
                    exception.accessDeniedHandler(customAccessDeniedHandler); // 인가 에러
                });

            /*
            UsernamePasswordAuthentication 클래스 앞에 jwtAuthenticationFilter를 등록
            로그인 API 요청은 인증 인가 없이도 요청이 가능하도록 설정
             */
        http.authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/users/login/**", "/actuator/health").permitAll();
                    auth.anyRequest().authenticated();
                })
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // cors에러를 대응하기 위한 메소드
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedOriginPatterns("*")
                        .allowedMethods("*");
            }
        };
    }

}