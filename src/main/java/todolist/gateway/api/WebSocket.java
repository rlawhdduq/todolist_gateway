package todolist.gateway.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;


@Controller
public class WebSocket {
    @Autowired
    private SimpMessagingTemplate message;

    private static final Logger log = LoggerFactory.getLogger(WebSocket.class);

    @MessageMapping("/board/{topic}")
    public void sendMessage(String msg, @DestinationVariable String topic)
    {
        log.info("도착했다. " + topic);
        return;
        // message.convertAndSend("/topic/" + topic, msg);
    }
}
