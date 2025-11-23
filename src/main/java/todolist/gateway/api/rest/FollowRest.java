package todolist.gateway.api.rest;

import org.springframework.web.bind.annotation.RestController;
import todolist.gateway.service.rest.GatewayService;

import java.util.HashMap;
import java.util.List;
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
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/v1/follow")
public class FollowRest {
    @Autowired
    private GatewayService gateway;

    private static final Logger log = LoggerFactory.getLogger(FollowRest.class);

    @RequestMapping(path="/{userId}", method=RequestMethod.GET)
    public ResponseEntity<Map<String, List<Long>>> getFollow(@PathVariable Long userId) {
        Map<String, List<Long>> res = gateway.get(userId, "follow", "", Map.class);
        return ResponseEntity.ok()
                            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                            .body(res);
    }

    @RequestMapping(method=RequestMethod.POST)
    public String insertFollow(@RequestBody Map<String, Object> data) {
        String res = gateway.post(data, "follow", "", String.class);
        return res;
    }

    // 얘는 친구 삭제니까 값을 두개 받아야함... 나와 삭제 할 친구의 PK
    @RequestMapping(method=RequestMethod.DELETE)
    public String deleteFollow(@RequestParam Map<String, Object> data) {
        String res = gateway.deleteObject(data, "follow", "", String.class);
        return res;
    }

    @RequestMapping(path="/state", method=RequestMethod.GET)
    public Boolean followState(@RequestParam("target") Long target_user_id, @RequestParam("source") Long source_user_id)
    {
        Map<String, Object> data = new HashMap();
        data.put("target", target_user_id);
        data.put("source", source_user_id);
        Boolean res = gateway.getObject(data, "follow", "/state", Boolean.class);
        return res;
    }
}
