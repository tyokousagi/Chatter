package com.example.chatter.config;

import java.util.UUID;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity // Spring Security を有効にするアノテーション
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((authorize) -> authorize
                // 認証が不要なパスを指定
                //.requestMatchers("/", "/css/**").permitAll() // 例: トップページと CSS は誰でもアクセス可能
                // 他のパスは認証が必要
                .anyRequest().authenticated()
            )
            .formLogin((form) -> form
                // ログインページ、ログイン処理URL、ログアウト処理URL などを設定
                .loginPage("/login") // 例: ログインページのパス
                .loginProcessingUrl("/authentication")
                .usernameParameter("usernameInput")
                .passwordParameter("passwordInput")

                .defaultSuccessUrl("/") // 例: ログイン成功後のリダイレクト先
                .failureForwardUrl("/login?error")

                .permitAll()
            )
            .rememberMe()
                .key(UUID.randomUUID().toString()) // Cookie の暗号化に使用する秘密鍵 (必須)
                .tokenValiditySeconds(8640000) // Cookie の有効期限 (秒単位、例: 1 日)
                .rememberMeParameter("remember-me") // ログイン画面のチェックボックスの名前
                .alwaysRemember(true) // ★ この行を追加
                .and()

            .logout((logout) -> logout
                // ログアウト時の処理を設定

                .logoutSuccessUrl("/login?logout") // 例: ログアウト後のリダイレクト先
                .invalidateHttpSession(true)
                .deleteCookies("JESSIONID")
                .permitAll()
            );
            

        return http.build();
    }
}
