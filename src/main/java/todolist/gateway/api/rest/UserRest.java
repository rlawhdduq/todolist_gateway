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
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/api/v1/user")
public class UserRest {

    @Autowired
    private GatewayService gateway;
    private static final Logger log = LoggerFactory.getLogger(UserRest.class);

    @RequestMapping(method=RequestMethod.POST)
    public String insertUser(@RequestBody Map<String, Object> data) {
        String res = gateway.post(data, "user", "");
        return res;
    }

    @RequestMapping(path="/login", method=RequestMethod.POST)
    public String loginUser(@RequestBody Map<String, Object> data) {
        String res = gateway.post(data, "user", "/login");
        return res;
    }

    @RequestMapping(path="/{userId}", method=RequestMethod.GET)
    public String getUser(@PathVariable Long userId) {
        String res = gateway.get(userId, "user", "");
        return res;
    }
}
