package error.pirate.backend.config;

import error.pirate.backend.security.*;
import error.pirate.backend.security.filter.CustomAuthenticationFilter;
import error.pirate.backend.security.filter.JwtFilter;
import error.pirate.backend.service.UserService;
import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserService userService;
    private final Environment env;
    private final JwtUtil jwtUtil;

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> {
                    authorize.requestMatchers(
                                    new AntPathRequestMatcher("/login", "POST"),
                                    new AntPathRequestMatcher("/api/v1/user", "POST")).permitAll() // login 요청만이 모든 사용자가 요청할 수 있다..
                            .requestMatchers(new AntPathRequestMatcher("/**"))
                            .authenticated(); // 위의 요청 외에는 인증만 필요하다.
                })
                /* session 로그인 방식을 사용하지 않음(JWT Token 방식을 사용할 예정) */
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // 커스텀 로그인 필터 이전에 JWT 토큰 확인 필터를 설정
        http.addFilterBefore(new JwtFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);
        http.addFilterAfter(getAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        // Exception 발생 시 핸들러 추가
        http.exceptionHandling(
                exceptionHandling -> {
                    exceptionHandling.accessDeniedHandler(new JwtAccessDeniedHandler());
                    exceptionHandling.authenticationEntryPoint(new JwtAuthenticationEntryPoint());
                }
        );
        return http.build();
    }

    private Filter getAuthenticationFilter() {

        CustomAuthenticationFilter filter = new CustomAuthenticationFilter();
        filter.setAuthenticationManager(getAuthenticationManager());
        filter.setAuthenticationSuccessHandler(new LoginSuccessHandler(env));
        filter.setAuthenticationFailureHandler(new LoginFailureHandler());

        return filter;
    }

    private AuthenticationManager getAuthenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder); // BcryptPasswordEncoder를 사용하여 비밀번호 인코딩 하도록 지정
        provider.setUserDetailsService(userService);  // userService를 UserDeatilService로 지정

        return new ProviderManager(provider);
    }

    @Bean
    public CorsFilter corsFilter() {
        return new CorsFilter(corsConfigurationSource());
    }

    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin(env.getProperty("frontend")); // 허용할 도메인
        config.addAllowedHeader("*"); // 모든 헤더 허용
        config.addAllowedMethod("*"); // 모든 HTTP 메소드 허용

        config.addExposedHeader("token");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}