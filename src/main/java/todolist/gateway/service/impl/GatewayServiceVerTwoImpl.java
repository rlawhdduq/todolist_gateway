package todolist.gateway.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import todolist.gateway.service.GatewayServiceVerTwo;

@Service
public class GatewayServiceVerTwoImpl implements GatewayServiceVerTwo{

    @Autowired
    private WebClient webClient;
    ObjectMapper objectMapper = new ObjectMapper();
    @Value("${board.url}")
    private String boardUrl;

    @Override
    public String get(Long boardId)
    {
        String callRes = webClient.get()
                                 .uri(uriBuilder -> uriBuilder.path(boardUrl + "/{boardId}").build(boardId))
                                 .retrieve()
                                 .bodyToMono(String.class).block();
        return callRes;
    }

    @Override
    public String post(Map<String, Object> data)
    {
        String callRes = webClient.post()
                                .uri(boardUrl)
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(data)
                                .retrieve()
                                .bodyToMono(String.class).block();
        return callRes;
    }
    
    @Override
    public String put(Map<String, Object> data)
    {
        String callRes = webClient.put()
                                .uri(boardUrl)
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(data)
                                .retrieve()
                                .bodyToMono(String.class).block();
        return callRes;
    }

    @Override
    public String delete(Long boardId)
    {
        String callRes = webClient.delete()
                                .uri(uriBuilder -> uriBuilder.path(boardUrl + "/{boardId}").build(boardId))
                                .retrieve()
                                .bodyToMono(String.class).block();
        return callRes;
    }
}
