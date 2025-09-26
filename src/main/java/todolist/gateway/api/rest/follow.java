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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@RestController
@RequestMapping("/api/v1/follow")
public class follow {
    @Autowired
    private GatewayService gateway;

    private static final Logger log = LoggerFactory.getLogger(follow.class);

    @RequestMapping(path="/{userId}", method=RequestMethod.GET)
    public String getFollow(@PathVariable Long userId) {
        String res = gateway.get(userId, "follow", "");
        return res;
    }

    @RequestMapping(method=RequestMethod.POST)
    public String insertFollow(@RequestBody Map<String, Object> data) {
        String res = gateway.post(data, "follow", "");
        return res;
    }

    // 얘는 친구 삭제니까 값을 두개 받아야함... 나와 삭제 할 친구의 PK
    @RequestMapping(path="/{userId}", method=RequestMethod.GET)
    public String deleteFollow(@RequestBody Map<String, Object> data) {
        String res = gateway.post(data, "follow", "");
        return res;
    }
}
