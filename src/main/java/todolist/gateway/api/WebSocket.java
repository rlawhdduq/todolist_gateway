package todolist.gateway.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocket {
    @Autowired
    private SimpMessagingTemplate message;

    @MessageMapping("/chat/board/{topic}")
    public void sendMessage(String msg, String topic)
    {
        message.convertAndSend("/topic/" + topic, msg);
    }
}
