package top.scxy.fusion.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import top.scxy.fusion.model.ChatMessage;

// 事件监听类
@Component
public class WebSocketEventListener {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);
    // 用于向客户端发送消息
    @Autowired
    private SimpMessageSendingOperations messagingTemplate;
    // 监听客户端连接事件
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        logger.info("Received a new web socket connection");
    }
    // 监听客户端断开连接事件
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        // 获取断开连接的用户
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) headerAccessor.getSessionAttributes().get("username");
        // 如果用户存在，则向所有订阅了该路径的客户端发送用户离开的消息
        if (username != null) {
            logger.info("User Disconnected: " + username);
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setType(ChatMessage.MessageType.LEAVE);
            chatMessage.setSender(username);
            // 将消息发送给所有订阅了该路径的客户端
            messagingTemplate.convertAndSend("/topic/public", chatMessage);
        }
    }
}
