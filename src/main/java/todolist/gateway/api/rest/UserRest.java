package todolist.gateway.api.rest;

import org.springframework.web.bind.annotation.RestController;

import todolist.gateway.service.rest.GatewayService;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@RestController
@RequestMapping("/api/v1/user")
public class UserRest {

    @Autowired
    private GatewayService gateway;
    private static final Logger log = LoggerFactory.getLogger(UserRest.class);

    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<String> insertUser(@RequestBody Map<String, Object> data) {
        String res = gateway.post(data, "user", "", String.class);
        return ResponseEntity.ok()
                            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                            .body(res);
    }

    @RequestMapping(path="/login", method=RequestMethod.POST)
    public ResponseEntity<String> loginUser(@RequestBody Map<String, Object> data) {
        String res = gateway.post(data, "user", "/login", String.class);
        return ResponseEntity.ok()
                            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                            .body(res);
    }

    @RequestMapping(path="/{userId}", method=RequestMethod.GET)
    public ResponseEntity<String> getUser(@PathVariable Long userId) {
        String res = gateway.get(userId, "user", "", String.class);
        return ResponseEntity.ok()
                            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                            .body(res);
    }
}
