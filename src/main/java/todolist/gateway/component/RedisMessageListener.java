// package todolist.gateway.component;

// import java.nio.charset.StandardCharsets;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.data.redis.connection.Message;
// import org.springframework.data.redis.connection.MessageListener;
// import org.springframework.messaging.simp.SimpMessagingTemplate;
// import org.springframework.stereotype.Component;


// @Component
// public class RedisMessageListener implements MessageListener{
    
//     private final SimpMessagingTemplate messageTemplate;
//     private static final Logger log = LoggerFactory.getLogger(RedisMessageListener.class);

//     @Autowired
//     public RedisMessageListener(SimpMessagingTemplate messagingTemplate)
//     {
//         this.messageTemplate = messagingTemplate;
//     }

//     @Override
//     public void onMessage(Message message, byte[] pattern)
//     {
//         String topic = new String(pattern);
//         String payload = new String(message.getBody());
//         log.info("topic = " + topic);
//         // log.info("payload = " + payload);

//         messageTemplate.convertAndSend("/"+topic, new String(payload.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8));
//     }
// }

// 비동기 메시지 큐 방식(카프카, 레디스 사용)에서 RestApi 방식으로 구현방향을 변경함에 따라 기존에 사용하던 의존성들을 주석처리한다.
// 비동기 메시지 큐 방식은 RestApi 방식으로 구현을 마친 후 성능개선을 위해 해당방식으로 변경할 때 다시 주석을 풀고 사용하자.