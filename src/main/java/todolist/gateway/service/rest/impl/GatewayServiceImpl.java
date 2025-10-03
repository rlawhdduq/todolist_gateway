package todolist.gateway.service.rest.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.ObjectMapper;

import todolist.gateway.service.rest.GatewayService;

@Service("RestGatewayService")
public class GatewayServiceImpl implements GatewayService{

    @Autowired
    private WebClient webClient;
    ObjectMapper objectMapper = new ObjectMapper();

    @Value("${user.url}")
    private String userUrl;
    @Value("${authinformation.url}")
    private String authUrl;
    @Value("${board.url}")
    private String boardUrl;
    @Value("${follow.url}")
    private String followUrl;
    @Value("${message.url}")
    private String messageUrl;
    @Value("${notification.url}")
    private String notificationUrl;

    @Override
    public String get(Long primaryKey, String api, String extUrl)
    {
        String callUrl = callUrl(api);
        String callRes = webClient.get()
                                 .uri(uriBuilder -> uriBuilder.path(String.format("%s/{primary}", callUrl+extUrl)).build(primaryKey))
                                 .retrieve()
                                 .bodyToMono(String.class).block();
        return callRes;
    }

    @Override
    public String getObject(Map<String, Object> data, String api, String extUrl)
    {
        String callUrl = callUrl(api);
        MultiValueMap<String, Object> mData = new LinkedMultiValueMap<>();
        mData.setAll(data);

        String callRes = webClient.get()
                                 .uri(uriBuilder -> {
                                                        uriBuilder.path(callUrl+extUrl);
                                                        // queryParams는 string,string밖에 못받기 때문에 object는 map을 순회시키면서 값을 할당하도록 한다.
                                                        mData.forEach((key, value) -> uriBuilder.queryParam(key, value)); 
                                                        return uriBuilder.build();
                                                    })
                                 .retrieve()
                                 .bodyToMono(String.class).block();
        return callRes;
    }

    @Override
    public String post(Map<String, Object> data, String api, String extUrl)
    {
        String callUrl = callUrl(api);
        String callRes = webClient.post()
                                .uri(callUrl+extUrl)
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(data)
                                .retrieve()
                                .bodyToMono(String.class).block();
        return callRes;
    }
    
    @Override
    public String put(Map<String, Object> data, String api, String extUrl)
    {
        String callUrl = callUrl(api);
        String callRes = webClient.put()
                                .uri(callUrl+extUrl)
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(data)
                                .retrieve()
                                .bodyToMono(String.class).block();
        return callRes;
    }

    @Override
    public String delete(Long primaryKey, String api, String extUrl)
    {
        String callUrl = callUrl(api);
        String callRes = webClient.delete()
                                .uri(uriBuilder -> uriBuilder.path(String.format("%s/{primary}", callUrl+extUrl)).build(primaryKey))
                                .retrieve()
                                .bodyToMono(String.class).block();
        return callRes;
    }

    @Override
    public String deleteObject(Map<String, Object> data, String api, String extUrl)
    {
        String callUrl = callUrl(api);
        MultiValueMap<String, Object> mData = new LinkedMultiValueMap<>();
        mData.setAll(data);

        String callRes = webClient.delete()
                                 .uri(uriBuilder -> {
                                                        uriBuilder.path(callUrl+extUrl);
                                                        // queryParams는 string,string밖에 못받기 때문에 object는 map을 순회시키면서 값을 할당하도록 한다.
                                                        mData.forEach((key, value) -> uriBuilder.queryParam(key, value)); 
                                                        return uriBuilder.build();
                                                    })
                                 .retrieve()
                                 .bodyToMono(String.class).block();
        return callRes;
    }

    private String callUrl(String api)
    {
        String callUrl = "";
        switch(api){
            case "board": 
                callUrl = boardUrl; break;
            case "user" : 
                callUrl = userUrl; break;
            case "auth" : 
                callUrl = authUrl; break;
            case "follow":
                callUrl = followUrl; break;
            default:
        }       
        return callUrl+="/rest";
    }
}
