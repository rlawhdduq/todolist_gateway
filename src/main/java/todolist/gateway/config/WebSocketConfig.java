package todolist.gateway.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;


@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer{
    
    @Value("${allow.host.url}")
    private String allowHostUrl;

    // 헷갈리지 말자... 이건 클라이언트와의 websocket용 설정이다..

    private static final Logger log = LoggerFactory.getLogger(WebSocketConfig.class);
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config)
    {
        /*
         * 서비스에서 설정하는 topic을 타겟으로 지정하면 된다
         * ex. Back에서 redisTemplate.convertAndSend("board/"+var1, data)로 보내고 있다면
         * enableSimplBroker에 /board경로를 추가해주면 된다.
         */
        config.enableSimpleBroker("/board"); // 브로커 설정
        // config.setApplicationDestinationPrefixes("/app"); // 클라이언트가 메시지를 보낼 prefix -> 클라이언트의 요청용 url이라고 보면 된다.
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry)
    {
        registry.addEndpoint("/ws").setAllowedOriginPatterns(allowHostUrl);
    }
}
