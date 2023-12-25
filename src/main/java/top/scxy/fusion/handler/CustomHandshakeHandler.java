package top.scxy.fusion.handler;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;

public class CustomHandshakeHandler extends DefaultHandshakeHandler {
    @Override
    protected Principal determineUser(org.springframework.http.server.ServerHttpRequest request,
                                      org.springframework.web.socket.WebSocketHandler wsHandler,
                                      java.util.Map<String, Object> attributes) {
        // 从已经登录的HttpSession中获取用户信息
        ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
        HttpSession session = servletRequest.getServletRequest().getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        attributes.put("userId", userId);
        return ()-> String.valueOf(userId);
    }
}
