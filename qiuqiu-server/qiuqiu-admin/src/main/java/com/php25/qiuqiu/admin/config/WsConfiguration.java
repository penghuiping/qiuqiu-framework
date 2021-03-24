package com.php25.qiuqiu.admin.config;


import com.php25.common.core.exception.Exceptions;
import com.php25.common.core.mess.IdGenerator;
import com.php25.common.core.mess.IdGeneratorImpl;
import com.php25.common.core.util.StringUtil;
import com.php25.common.mq.MessageQueueManager;
import com.php25.common.redis.RedisManager;
import com.php25.common.timer.Timer;
import com.php25.common.ws.GlobalSession;
import com.php25.common.ws.InnerMsgRetryQueue;
import com.php25.common.ws.MsgDispatcher;
import com.php25.common.ws.RedisQueueSubscriber;
import com.php25.common.ws.RegisterHandlerConfig;
import com.php25.common.ws.SecurityAuthentication;
import com.php25.common.ws.WebsocketHandler;
import com.php25.qiuqiu.user.constant.UserErrorCode;
import com.php25.qiuqiu.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;


@Slf4j
@Configuration
@ConditionalOnProperty({"ws.enable", "server.id"})
@EnableWebSocket
public class WsConfiguration implements WebSocketConfigurer {

    @Value("${server.id}")
    private String serverId;

    @Autowired
    private WebsocketHandler websocketHandler;

    @Autowired
    private MsgDispatcher msgDispatcher;

    @Autowired
    private ConfigurableApplicationContext applicationContext;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(websocketHandler, "/websocket").setAllowedOrigins("*");
    }


    @Bean
    public RegisterHandlerConfig registerHandlerConfig() {
        RegisterHandlerConfig registerHandlerConfig = new RegisterHandlerConfig(msgDispatcher, applicationContext);
        registerHandlerConfig.scanPackage("com.php25.common.ws", "com.php25.qiuqiu.notify.dto.ws");
        return registerHandlerConfig;
    }

    @Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        container.setMaxTextMessageBufferSize(8192);
        container.setMaxBinaryMessageBufferSize(8192);
        return container;
    }

    @Bean
    public MsgDispatcher msgDispatcher() {
        return new MsgDispatcher();
    }

    @Bean
    public InnerMsgRetryQueue innerMsgRetryQueue() {
        return new InnerMsgRetryQueue();
    }

    @Bean
    public GlobalSession globalSession(RedisManager redisManager,
                                       MsgDispatcher msgDispatcher,
                                       SecurityAuthentication securityAuthentication,
                                       InnerMsgRetryQueue innerMsgRetryQueue,
                                       Timer timer,
                                       MessageQueueManager messageQueueManager
    ) {
        return new GlobalSession(innerMsgRetryQueue,
                redisManager,
                securityAuthentication,
                serverId,
                msgDispatcher,
                timer,
                messageQueueManager);
    }

    @Bean
    public RedisQueueSubscriber redisQueueSubscriber(RedisManager redisManager, InnerMsgRetryQueue innerMsgRetryQueue) {
        return new RedisQueueSubscriber(redisManager, serverId, innerMsgRetryQueue);
    }

    @Bean
    public WebsocketHandler websocketHandler(GlobalSession globalSession, InnerMsgRetryQueue innerMsgRetryQueue) {
        return new WebsocketHandler(globalSession, innerMsgRetryQueue);
    }

    @Bean
    public SecurityAuthentication securityAuthentication(UserService userService) {
        return new SecurityAuthentication() {
            @Override
            public String authenticate(String token) {
                if (userService.isTokenValid(token)) {
                    String username = userService.getUsernameFromJwt(token);
                    if (!StringUtil.isBlank(username)) {
                        log.info("ws握手认证成功,用户名:{}", username);
                        return username;
                    }
                }
                throw Exceptions.throwBusinessException(UserErrorCode.JWT_ILLEGAL);
            }
        };
    }

    @Bean
    public IdGenerator idGenerator() {
        return new IdGeneratorImpl();
    }
}
