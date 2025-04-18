package com.example.chatter.config;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * HTTPからHTTPSへのリダイレクト設定クラス
 * HTTPポート(8080)へのアクセスをHTTPSポート(8443)へリダイレクトする
 */
@Configuration
public class HttpToHttpsRedirectConfig {

    private final Environment environment;

    public HttpToHttpsRedirectConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public ServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
            @Override
            protected void postProcessContext(Context context) {
                // IPアドレスベースの接続でもHTTPSリダイレクトを適用する
                SecurityConstraint securityConstraint = new SecurityConstraint();
                securityConstraint.setUserConstraint("CONFIDENTIAL");
                SecurityCollection collection = new SecurityCollection();
                collection.addPattern("/*");
                securityConstraint.addCollection(collection);
                context.addConstraint(securityConstraint);
                
                System.out.println("HTTPSリダイレクト設定が有効化されました。");
            }
        };
        
        // HTTP ポート 8080 のコネクタを追加
        tomcat.addAdditionalTomcatConnectors(createHttpConnector());
        return tomcat;
    }

    private Connector createHttpConnector() {
        Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
        connector.setScheme("http");
        connector.setPort(8080);           // HTTP用ポート
        connector.setSecure(false);
        connector.setRedirectPort(8443);   // HTTPS用ポートへのリダイレクト
        return connector;
    }
    
    // サーバーのホスト名を取得
    private String getServerHostname() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            return "localhost";
        }
    }
}
