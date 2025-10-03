package todolist.gateway.api.mq;

import org.springframework.web.bind.annotation.RestController;

import todolist.gateway.service.mq.GatewayService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/api/v2/follow")
public class FollowMq {
    @Autowired
    private GatewayService gateway;

    private static final Logger log = LoggerFactory.getLogger(FollowMq.class);

}
