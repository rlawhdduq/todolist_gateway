package todolist.gateway.api.rest;

import org.springframework.web.bind.annotation.RestController;

import todolist.gateway.dto.BoardDto;
import todolist.gateway.service.rest.GatewayService;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@RestController
@RequestMapping("/api/v1/noti")
public class NotificationRest {

    private static final Logger log = LoggerFactory.getLogger(NotificationRest.class);

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @RequestMapping(value="/noti", method=RequestMethod.POST)
    public ResponseEntity<String> notiBoard(@RequestBody BoardDto boardDto) {
        Map<String, String> scope = new HashMap<String, String>(){{
            put("A", "all");
            put("F", "friends:");
            put("C", "community:");
        }};
        String topic = scope.get(boardDto.getScope_of_disclosure());
        if( !boardDto.getScope_of_disclosure().equals("A") )
        {
            topic += boardDto.getUser_id();
        }
        messagingTemplate.convertAndSend("board/"+topic, boardDto);
        return ResponseEntity.ok("["+LocalDateTime.now()+"]Notification sent to WebSocket clients");
    }
    
}
