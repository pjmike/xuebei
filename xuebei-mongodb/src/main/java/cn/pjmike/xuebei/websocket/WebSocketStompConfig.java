package cn.pjmike.xuebei.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;
import org.springframework.web.socket.handler.WebSocketHandlerDecorator;
import org.springframework.web.socket.handler.WebSocketHandlerDecoratorFactory;

/**
 * 启用STOMP的配置类
 *
 * @author pjmike
 * @create 2018-02-16 20:24
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketStompConfig extends AbstractWebSocketMessageBrokerConfigurer{
    private static final Logger logger = LoggerFactory.getLogger(WebSocketStompConfig.class);

    /**
     * 前端首先要连接stomp端点
     *
     * @param stompEndpointRegistry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
        //注册一个STOMP的endpoint端点，并指定使用SockJS协议,前端通过这个端点与服务器建立websocket连接
        stompEndpointRegistry.addEndpoint("/endpoint").withSockJS();
    }

    /**
     * MessageBrokerRegistry,配置消息代理
     * @param registry
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        super.configureMessageBroker(registry);
        //设置目的地前缀
        registry.enableSimpleBroker("/topic", "/queue");
        //设置应用程序前缀，前端发起的请求会自动添加/app
        registry.setApplicationDestinationPrefixes("/app");
    }

 /*   @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
        registration.addDecoratorFactory(new WebSocketHandlerDecoratorFactory() {
            @Override
            public WebSocketHandler decorate(WebSocketHandler webSocketHandler) {
                return new WebSocketHandlerDecorator(webSocketHandler) {
                    @Override
                    public void afterConnectionEstablished(final WebSocketSession session) throws Exception {
                        String username = session.getPrincipal().getName();
                        logger.info("online: " + username);
                        super.afterConnectionEstablished(session);
                    }

                    @Override
                    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus)
                            throws Exception {
                        String username = session.getPrincipal().getName();
                        logger.info("offline: " + username);
                        super.afterConnectionClosed(session, closeStatus);
                    }
                };
            }
        });
        super.configureWebSocketTransport(registration);
    }*/
}
