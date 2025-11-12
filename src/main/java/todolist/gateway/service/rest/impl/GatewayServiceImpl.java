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
import org.springframework.web.util.UriComponentsBuilder;

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
                        .uri(callUrl+extUrl+"/"+primaryKey)
                        .retrieve()
                        .bodyToMono(responseType).block();
    }
    @Override
    public <T> T get(Long primaryKey, String api, String extUrl, ParameterizedTypeReference<T> responseType)
    {
        String callUrl = callUrl(api);
        return webClient.get()
                        .uri(callUrl+extUrl+"/"+primaryKey)
                        .retrieve()
                        .bodyToMono(responseType).block();
    }

    @Override
    public <T> T getObject(Map<String, Object> data, String api, String extUrl, Class<T> responseType)
    {
        String callUrl = callUrl(api);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(callUrl + extUrl);

        MultiValueMap<String, Object> mData = new LinkedMultiValueMap<>();
        mData.setAll(data);
        mData.forEach((key, val) -> {
            if (val != null)
            {
                builder.queryParam(key, val.toString());
            }
        });
        
        return webClient.get()
                        .uri(builder.build().toUri())
                        .retrieve()
                        .bodyToMono(responseType).block();
    }
    @Override
    public <T> T getObject(Map<String, Object> data, String api, String extUrl, ParameterizedTypeReference<T> responseType)
    {
        String callUrl = callUrl(api);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(callUrl + extUrl);

        MultiValueMap<String, Object> mData = new LinkedMultiValueMap<>();
        mData.setAll(data);
        mData.forEach((key, val) -> {
            if (val != null)
            {
                builder.queryParam(key, val.toString());
            }
        });
        return webClient.get()
                        .uri(builder.build().toUri())
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
                                .uri(callUrl+extUrl, primaryKey)
                                .retrieve()
                                .bodyToMono(responseType).block();
    }
    @Override
    public <T> T delete(Long primaryKey, String api, String extUrl, ParameterizedTypeReference<T> responseType)
    {
        String callUrl = callUrl(api);
        return webClient.delete()
                                .uri(callUrl+extUrl, primaryKey)
                                .retrieve()
                                .bodyToMono(responseType).block();
    }

    @Override
    public <T> T deleteObject(Map<String, Object> data, String api, String extUrl, Class<T> responseType)
    {
        String callUrl = callUrl(api);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(callUrl + extUrl);

        MultiValueMap<String, Object> mData = new LinkedMultiValueMap<>();
        mData.setAll(data);
        mData.forEach((key, val) -> {
            if (val != null)
            {
                builder.queryParam(key, val.toString());
            }
        });
        return webClient.delete()
                                 .uri(builder.build().toUri())
                                 .retrieve()
                                 .bodyToMono(responseType).block();
    }
    @Override
    public <T> T deleteObject(Map<String, Object> data, String api, String extUrl, ParameterizedTypeReference<T> responseType)
    {
        String callUrl = callUrl(api);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(callUrl + extUrl);

        MultiValueMap<String, Object> mData = new LinkedMultiValueMap<>();
        mData.setAll(data);
        mData.forEach((key, val) -> {
            if (val != null)
            {
                builder.queryParam(key, val.toString());
            }
        });

        return webClient.delete()
                                 .uri(builder.build().toUri())
                                 .retrieve()
                                 .bodyToMono(responseType).block();
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
        log.info("return Url["+callUrl+"]");
        return callUrl+="/rest";
    }
}
