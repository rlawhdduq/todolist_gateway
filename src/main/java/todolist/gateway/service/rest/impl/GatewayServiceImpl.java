package todolist.gateway.service.rest.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.ObjectMapper;

import todolist.gateway.service.rest.GatewayService;

@Service
public class GatewayServiceImpl implements GatewayService{

    @Autowired
    private WebClient webClient;
    ObjectMapper objectMapper = new ObjectMapper();

    @Value("${user.url}")
    private String userUrl;
    @Value("${auth.url}")
    private String authUrl;
    @Value("${board.url}")
    private String boardUrl;
    @Value("${follow.url}")
    private String followUrl;

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
