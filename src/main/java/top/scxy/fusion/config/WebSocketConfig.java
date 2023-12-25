package top.scxy.fusion.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import top.scxy.fusion.handler.CustomHandshakeHandler;

/*
* WebSocket配置类，用于配置WebSocket的相关信息
* */

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    // 注册STOMP协议的节点，并映射指定的URL
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setHandshakeHandler(new CustomHandshakeHandler())
                .withSockJS();  // 支持socketJs
    }
    // 配置消息代理，用于广播式消息
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic", "/user");
        registry.setApplicationDestinationPrefixes("/app");
//        registry.setUserDestinationPrefix("/user");
    }
}
