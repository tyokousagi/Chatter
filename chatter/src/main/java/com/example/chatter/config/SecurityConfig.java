package com.example.chatter.config;

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
            // チャネルセキュリティを設定 - すべてのリクエストをHTTPS経由にする
            .requiresChannel(channel -> channel.anyRequest().requiresSecure())
            .authorizeHttpRequests(authorize -> authorize
                // 認証が不要なパスを指定
                .requestMatchers("/", "/css/**", "/images/**", "/js/**", "/service-worker.js", "/api/push/**").permitAll() // トップページ、静的リソース、Push通知API全般は認証不要
                // 他のパスは認証が必要
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                // ログインページ、ログイン処理URL などを設定
                .loginPage("/login") // ログインページのパス
                .loginProcessingUrl("/authentication")
                .usernameParameter("usernameInput")
                .passwordParameter("passwordInput")
                .defaultSuccessUrl("/") // ログイン成功後のリダイレクト先
                .failureForwardUrl("/login?error")
                .permitAll()
            )
            // CSRFの例外を追加
            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/api/push/**") // プッシュ通知APIはCSRF保護の対象外にする
            )
            // rememberMeを設定
            .rememberMe(rememberMe -> {
                rememberMe.key("securityKey") // 固定の鍵
                    .tokenValiditySeconds(8640000) // Cookie の有効期限 (秒単位、例: 100 日)
                    .rememberMeParameter("remember-me"); // ログイン画面のチェックボックスの名前
            })
            .logout(logout -> logout
                // ログアウト時の処理を設定
                .logoutSuccessUrl("/login?logout") // ログアウト後のリダイレクト先
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            );

        return http.build();
    }
}
