package todolist.gateway.api.v2;

import org.springframework.web.bind.annotation.RestController;

import todolist.gateway.dto.BoardDto;
import todolist.gateway.service.GatewayService;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@RestController
@RequestMapping("/api/v1/service")
public class message {
    @Autowired
    private GatewayService gateway;

    private static final Logger log = LoggerFactory.getLogger(message.class);

    @RequestMapping(method=RequestMethod.POST)
    public String processCall(
        @RequestBody String data,
        @RequestHeader("call_url") String callUrl,
        @RequestHeader("call_method") String callMethod
                            ) 
    {
        log.info("process Call");
        return gateway.processCall(data, callUrl, callMethod);
    }
    
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
    
    // @RequestMapping(method=RequestMethod.GET)
    // public String getCall(
    //     @RequestBody String data,
    //     @RequestHeader("call_url") String callUrl,
    //     @RequestHeader("call_method") String callMethod
    //                     )  
    // {
    //     log.info("get Call");
    //     return gateway.get(data, callUrl, callMethod);
    // }
    // @RequestMapping(method=RequestMethod.POST)
    // public String postCall(
    //     @RequestBody String data,
    //     @RequestHeader("call_url") String callUrl,
    //     @RequestHeader("call_method") String callMethod
    //                     ) 
    // {
    //     log.info("post Call");
    //     return gateway.post(data, callUrl, callMethod);
    // }
    // @RequestMapping(method=RequestMethod.PUT)
    // public String putCall(
    //     @RequestBody String data,
    //     @RequestHeader("call_url") String callUrl,
    //     @RequestHeader("call_method") String callMethod
    //                     )
    // {
    //     log.info("put Call");
    //     return gateway.put(data, callUrl, callMethod);
    // }
    // @RequestMapping(method=RequestMethod.DELETE)
    // public String deleteCall(
    //     @RequestBody String data,
    //     @RequestHeader("call_url") String callUrl,
    //     @RequestHeader("call_method") String callMethod
    //                         )
    // {
    //     log.info("delete Call");
    //     return gateway.delete(data, callUrl, callMethod);
    // }
}
