package todolist.gateway.api;

import org.springframework.web.bind.annotation.RestController;

import todolist.gateway.service.GatewayService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
@RequestMapping("/api/v1/service")
public class Gateway {
    @Autowired
    private GatewayService gateway;

    private static final Logger log = LoggerFactory.getLogger(Gateway.class);

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
