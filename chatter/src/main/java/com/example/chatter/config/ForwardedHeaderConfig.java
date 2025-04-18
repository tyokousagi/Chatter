package com.example.chatter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ForwardedHeaderFilter;

/**
 * X-Forwarded-* ヘッダーを処理するための設定クラス
 * プロキシサーバーを使用する環境や、IPアドレスでアクセスする場合に
 * HTTPSリダイレクトが正しく機能するようにします
 */
@Configuration
public class ForwardedHeaderConfig {

    @Bean
    public ForwardedHeaderFilter forwardedHeaderFilter() {
        // X-Forwarded-Host, X-Forwarded-Port, X-Forwarded-Proto などのヘッダーを処理
        return new ForwardedHeaderFilter();
    }
}
