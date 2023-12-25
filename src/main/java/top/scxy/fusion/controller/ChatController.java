package top.scxy.fusion.controller;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import top.scxy.fusion.model.ChatMessage;

// 信息处理类
@Controller
public class ChatController {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private SimpUserRegistry simpUserRegistry;
    private Logger logger = org.slf4j.LoggerFactory.getLogger(ChatController.class);
    @GetMapping("/chat")
    public String chat() {
        return "chat";
    }
    // 将接收到的信息广播给所有订阅了该路径的客户端
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        logger.info("public message from :" + chatMessage.getSender() + " to :" + chatMessage.getReceiver() + " content: " + chatMessage.getMsg());
        return chatMessage;
    }
    // 在用户加入聊天室时，将用户信息存入WebSocket会话中
    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage,
                               SimpMessageHeaderAccessor headerAccessor) {
//        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        System.out.println("addUser: " + headerAccessor.getSessionAttributes().get("userId"));
        return chatMessage;
    }
    // 将接收到的私聊消息发送给消息中的对应用户
    @MessageMapping("/chat.sendPrivateMessage")
    public void sendPrivateMessage(@Payload ChatMessage chatMessage) {
        String Username = chatMessage.getReceiver();
        logger.info("private message from :" + chatMessage.getSender() + " to :" + Username + " content: " + chatMessage.getMsg());
        messagingTemplate.convertAndSendToUser(Username, "/private", chatMessage);
        // 同时也要发送给自己才能显示在聊天界面上
        messagingTemplate.convertAndSendToUser(chatMessage.getSender(), "/private", chatMessage);
        // messagingTemplate.convertAndSendToUser(username, topic, chatMessage);
        // 实际相当于 messagingTemplate.convertAndSend("/user/" + username + topic, chatMessage);
        // 最终消息会发布在 /user/{username}/{topic} 路径上
    }
}
