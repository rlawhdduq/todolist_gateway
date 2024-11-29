package todolist.gateway.service.impl;

import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import todolist.gateway.exception.UrlNotFoundException;
import todolist.gateway.service.GatewayService;

@Service
public class GatewayServiceImpl implements GatewayService{

    @Autowired
    private WebClient webClient;
    @Autowired
    private ObjectMapper objectMapper;

    private static final Logger log = LoggerFactory.getLogger(GatewayServiceImpl.class);

    @Value("${user.url}")
    private String userUrl;
    @Value("${board.url}")
    private String boardUrl;
    @Value("${follow.url}")
    private String followUrl;
    @Value("${authinformation.url}")
    private String authinformationUrl;
    @Value("${message.url}")
    private String messageUrl;
    @Value("${notification.url}")
    private String notificationUrl;

    @Override
    public String processCall(String data, String callUrl, String callMethod)
    {
        String resBody = null;
        if(callMethod.equalsIgnoreCase("get")) resBody = get(data ,callUrl, callMethod);
        else if(callMethod.equalsIgnoreCase("post")) resBody = post(data, callUrl, callMethod);
        else if(callMethod.equalsIgnoreCase("put")) resBody = put(data, callUrl, callMethod);
        else if(callMethod.equalsIgnoreCase("delete")) resBody = delete(data, callUrl, callMethod);

        return resBody;
    }
    // @Override
    private String get(String data, String callUrl, String callMethod)
    {
        // log.info("get");
        callUrl = urlForwarding(callUrl);
        callUrl = pathvariableForwarding(data, callUrl);
        
        String callRes = webClient.get()
                                 .uri(callUrl)
                                 .retrieve()
                                 .bodyToMono(String.class).block();
        // log.info("get return : " + callRes);
        return callRes;
    }
    // @Override
    private String post(String data, String callUrl, String callMethod)
    {
        // log.info("post");
        callUrl = urlForwarding(callUrl);
        String requestBody = dataParse(data);

        String callRes = webClient.post()
                                .uri(callUrl)
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(requestBody)
                                .retrieve()
                                .bodyToMono(String.class).block();
        // log.info("post return : " + callRes);
        return callRes;
    }
    // @Override
    private String put(String data, String callUrl, String callMethod)
    {
        // log.info("put");
        callUrl = urlForwarding(callUrl);
        String requestBody = dataParse(data);
            
        String callRes = webClient.put()
                                .uri(callUrl)
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(requestBody)
                                .retrieve()
                                .bodyToMono(String.class).block();
        // log.info("put return :" + callRes);
        return callRes;
    }
    // @Override
    private String delete(String data, String callUrl, String callMethod)
    {
        // log.info("delete");
        callUrl = urlForwarding(callUrl);
        String requestBody = dataParse(data);

        String callRes = webClient.method(HttpMethod.DELETE)
                                .uri(callUrl)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(BodyInserters.fromValue(requestBody))
                                .retrieve()
                                .bodyToMono(String.class).block();
        // log.info("delete return : " + callRes);
        return callRes;
    }

    private String urlForwarding(String callUrl)
    {
        // 문제발생...
        // PathVariable처리
        // Get  - /api/board/{user_id}/{limit}/{board_id}
        //      - /api/board/{user_id}/{board_id}
        //      - /api/board/reply/{board_id} -> 개발 X 사용여부 불확실
        //      - /api/follow/{user_id}
        String returnUrl = "";
        if(callUrl.contains("/api/board")) 
            returnUrl = boardUrl+callUrl;
        else if(callUrl.contains("/api/follow")) 
            returnUrl = followUrl+callUrl;
        else if(callUrl.contains("/api/user"))
            returnUrl = userUrl+callUrl;
        else if(callUrl.contains("/api/token"))
            returnUrl = authinformationUrl+callUrl;
        else if(callUrl.contains("/api/message"))
            returnUrl = messageUrl+callUrl;
        else if(callUrl.contains("/api/notification"))
            returnUrl = notificationUrl+callUrl;
        else
            throw new UrlNotFoundException("요청 URL 유효성 검증 실패");

        log.info("returnUrl : "+returnUrl);
        return returnUrl;
    }
    
    private String pathvariableForwarding(String data, String callUrl)
    {
        // log.info("PathVariable 변환 시작");
        try
        {
            JsonNode rootNode = objectMapper.readTree(data);
            JsonNode bodyNode = rootNode.path("Body");
            Iterator<Map.Entry<String, JsonNode>> fields = bodyNode.fields();

            int cnt = 0;
            callUrl += "?";
            while(fields.hasNext())
            {
                Map.Entry<String, JsonNode> field = fields.next();
                callUrl += field.getKey()+"="+field.getValue().asText()+"&";
                log.info("call Url = "+callUrl);
                if(cnt > 100)
                {
                    System.out.println("와일문 무한반복에 빠졌습니다. <- 해당 문자열을 검색해서 처리하시기바랍니다.");
                    break;
                }
                cnt++;
            }
        }
        catch(JsonProcessingException e)
        {
            System.out.println("Json 변환 과정 예외");
        }
        return callUrl;
    }

    private String dataParse(String data)
    {
        String requestBody = "";
        try
        {
            JsonNode rootNode = objectMapper.readTree(data);
            JsonNode bodyNode = rootNode.path("Body");
            requestBody = objectMapper.writeValueAsString(bodyNode);
        }
        catch(JsonProcessingException e)
        {
            log.info("Json 변환 과정 예외");
        }
        return requestBody;
    }
}
