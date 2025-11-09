package todolist.gateway.service.rest.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.ObjectMapper;

import todolist.gateway.api.rest.AuthinformationRest;
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

    private static final Logger log = LoggerFactory.getLogger(GatewayServiceImpl.class);

    @Override
    public <T> T get(Long primaryKey, String api, String extUrl, Class<T> responseType)
    {
        String callUrl = callUrl(api);
        return webClient.get()
                        .uri(uriBuilder -> uriBuilder.path(String.format("%s/{primary}", callUrl+extUrl)).build(primaryKey))
                        .retrieve()
                        .bodyToMono(responseType).block();
    }
    @Override
    public <T> T get(Long primaryKey, String api, String extUrl, ParameterizedTypeReference<T> responseType)
    {
        String callUrl = callUrl(api);
        return webClient.get()
                        .uri(uriBuilder -> uriBuilder.path(String.format("%s/{primary}", callUrl+extUrl)).build(primaryKey))
                        .retrieve()
                        .bodyToMono(responseType).block();
    }

    @Override
    public <T> T getObject(Map<String, Object> data, String api, String extUrl, Class<T> responseType)
    {
        String callUrl = callUrl(api);
        MultiValueMap<String, Object> mData = new LinkedMultiValueMap<>();
        mData.setAll(data);
        return webClient.get()
                        .uri(uriBuilder -> {
                                            uriBuilder.path(callUrl+extUrl);
                                            // queryParams는 string,string밖에 못받기 때문에 object는 map을 순회시키면서 값을 할당하도록 한다.
                                            mData.forEach((key, value) -> uriBuilder.queryParam(key, value)); 
                                            return uriBuilder.build();
                                        })
                        .retrieve()
                        .bodyToMono(responseType).block();
    }
    @Override
    public <T> T getObject(Map<String, Object> data, String api, String extUrl, ParameterizedTypeReference<T> responseType)
    {
        String callUrl = callUrl(api);
        MultiValueMap<String, Object> mData = new LinkedMultiValueMap<>();
        mData.setAll(data);
        return webClient.get()
                        .uri(uriBuilder -> {
                                            uriBuilder.path(callUrl+extUrl);
                                            // queryParams는 string,string밖에 못받기 때문에 object는 map을 순회시키면서 값을 할당하도록 한다.
                                            mData.forEach((key, value) -> uriBuilder.queryParam(key, value)); 
                                            return uriBuilder.build();
                                        })
                        .retrieve()
                        .bodyToMono(responseType).block();
    }

    @Override
    public <T> T post(Map<String, Object> data, String api, String extUrl, Class<T> responseType)
    {
        String callUrl = callUrl(api);
        return webClient.post()
                                .uri(callUrl+extUrl)
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(data)
                                .retrieve()
                                .bodyToMono(responseType).block();
    }
    @Override
    public <T> T post(Map<String, Object> data, String api, String extUrl, ParameterizedTypeReference<T> responseType)
    {
        String callUrl = callUrl(api);
        return webClient.post()
                                .uri(callUrl+extUrl)
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(data)
                                .retrieve()
                                .bodyToMono(responseType).block();
    }
    
    @Override
    public <T> T put(Map<String, Object> data, String api, String extUrl, Class<T> responseType)
    {
        String callUrl = callUrl(api);
        return webClient.put()
                                .uri(callUrl+extUrl)
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(data)
                                .retrieve()
                                .bodyToMono(responseType).block();
    }
    @Override
    public <T> T put(Map<String, Object> data, String api, String extUrl, ParameterizedTypeReference<T> responseType)
    {
        String callUrl = callUrl(api);
        return webClient.put()
                                .uri(callUrl+extUrl)
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(data)
                                .retrieve()
                                .bodyToMono(responseType).block();
    }

    @Override
    public <T> T delete(Long primaryKey, String api, String extUrl, Class<T> responseType)
    {
        String callUrl = callUrl(api);
        return webClient.delete()
                                .uri(uriBuilder -> uriBuilder.path(String.format("%s/{primary}", callUrl+extUrl)).build(primaryKey))
                                .retrieve()
                                .bodyToMono(responseType).block();
    }
    @Override
    public <T> T delete(Long primaryKey, String api, String extUrl, ParameterizedTypeReference<T> responseType)
    {
        String callUrl = callUrl(api);
        return webClient.delete()
                                .uri(uriBuilder -> uriBuilder.path(String.format("%s/{primary}", callUrl+extUrl)).build(primaryKey))
                                .retrieve()
                                .bodyToMono(responseType).block();
    }

    @Override
    public <T> T deleteObject(Map<String, Object> data, String api, String extUrl, Class<T> responseType)
    {
        String callUrl = callUrl(api);
        MultiValueMap<String, Object> mData = new LinkedMultiValueMap<>();
        mData.setAll(data);
        return webClient.delete()
                                 .uri(uriBuilder -> {
                                                        uriBuilder.path(callUrl+extUrl);
                                                        // queryParams는 string,string밖에 못받기 때문에 object는 map을 순회시키면서 값을 할당하도록 한다.
                                                        mData.forEach((key, value) -> uriBuilder.queryParam(key, value)); 
                                                        return uriBuilder.build();
                                                    })
                                 .retrieve()
                                 .bodyToMono(responseType).block();
    }
    @Override
    public <T> T deleteObject(Map<String, Object> data, String api, String extUrl, ParameterizedTypeReference<T> responseType)
    {
        String callUrl = callUrl(api);
        MultiValueMap<String, Object> mData = new LinkedMultiValueMap<>();
        mData.setAll(data);
        return webClient.delete()
                                 .uri(uriBuilder -> {
                                                        uriBuilder.path(callUrl+extUrl);
                                                        // queryParams는 string,string밖에 못받기 때문에 object는 map을 순회시키면서 값을 할당하도록 한다.
                                                        mData.forEach((key, value) -> uriBuilder.queryParam(key, value)); 
                                                        return uriBuilder.build();
                                                    })
                                 .retrieve()
                                 .bodyToMono(responseType).block();
    }

    private String callUrl(String api)
    {
        String callUrl = "";
        log.info("url Check["+userUrl+"]");
        log.info("url Check["+authUrl+"]");
        log.info("url Check["+followUrl+"]");
        log.info("url Check["+boardUrl+"]");
        log.info("url Check["+messageUrl+"]");
        log.info("url Check["+notificationUrl+"]");
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
