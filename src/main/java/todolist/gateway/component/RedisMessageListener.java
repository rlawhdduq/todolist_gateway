package todolist.gateway.component;

import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;


@Component
public class RedisMessageListener implements MessageListener{
    
    private final SimpMessagingTemplate messageTemplate;
    private static final Logger log = LoggerFactory.getLogger(RedisMessageListener.class);

    @Autowired
    public RedisMessageListener(SimpMessagingTemplate messagingTemplate)
    {
        this.messageTemplate = messagingTemplate;
    }

    @Override
    public void onMessage(Message message, byte[] pattern)
    {
        String topic = new String(pattern);
        String payload = new String(message.getBody());
        log.info("topic = " + topic);
        // log.info("payload = " + payload);

        messageTemplate.convertAndSend("/"+topic, new String(payload.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8));
    }
}
