package com.example.chatter.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // クライアントからの送信先のプレフィックス
        config.setApplicationDestinationPrefixes("/app");
        // サーバー側からクライアントへ送信する際のトピックのプレフィックス
        config.enableSimpleBroker("/topic");
    }
    
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // WebSocket エンドポイントの設定。withSockJS() を呼ぶことで SockJS 互換も有効化
        registry.addEndpoint("/ws").withSockJS();
    }
}
