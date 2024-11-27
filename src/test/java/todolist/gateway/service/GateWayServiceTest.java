package todolist.gateway.service;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import todolist.gateway.service.impl.GatewayServiceImpl;

@SpringBootTest
@AutoConfigureMockMvc
public class GateWayServiceTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    private static final Logger log = LoggerFactory.getLogger(GateWayServiceTest.class);

    @Value("${jwt.secret.lawSecretKey}")
    private String lawSecretKey;

    private Map<String, Object> dto = new HashMap();
    private Map<String, Object> requestBody = new HashMap<>();
    private String request;
    private ResultActions result;
    private String token = "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiIxMDAiLCJpZCI6ImlqZW9uZ25hbSIsInVzZXJfdHlwZSI6Ik5DIiwiaWF0IjoxNzMyNjgzODM5LCJleHAiOjE3MzI2ODc0Mzl9.i9iwwt7oeh7VQJruAfuV-vtLBR0D-dpelzvk3AeFkeu2vy7loibl_u1gIWk_lun_";
    // @Test
    public void boardTest()
    throws Exception
    {
        log.info("key : "+lawSecretKey);
        dto.put("user_id", 100);
        dto.put("limit", 3);
        // dto.put("board_id", 1);
        requestBody.put("Body", dto);
        request = objectMapper.writeValueAsString(requestBody);
        result = mockMvc.perform(
                get("/api/v1/service")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
                .header("token", token)
                .header("call_url", "/api/board")
                .header("call_method", "GET")
            );
        String resultStr = result.andReturn().getResponse().getContentAsString();
        log.info("result = " + resultStr);
        ObjectMapper objMapper = new ObjectMapper();
        // JsonNode rootNode = objMapper.readTree(resultStr);
        // String bodyValue = rootNode.path("Body").toString();
        // log.info("token = " + bodyValue);
    }

    // @Test
    public void followTest()
    {
        log.info("key : "+lawSecretKey);
    }

    // @Test
    public void authTest()
    throws Exception
    {
        log.info("key : "+lawSecretKey);
        Long user_id = 100L;
        String id = "ijeongnam";
        String user_type = "NC";

        dto.put("user_id", user_id);
        dto.put("id", id);
        dto.put("user_type", user_type);
        requestBody.put("Body", dto);
        String request = objectMapper.writeValueAsString(requestBody);


        // andDo(print())는 return Type확인 안될 때 해보자
        ResultActions result = mockMvc.perform(
                post("/api/v1/service")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
                .header("token", "")
                .header("call_url", "/api/token")
                .header("call_method", "POST")
            );
        String resultStr = result.andReturn().getResponse().getContentAsString();
        ObjectMapper objMapper = new ObjectMapper();
        JsonNode rootNode = objMapper.readTree(resultStr);
        String bodyValue = rootNode.path("Body").asText();
        log.info("token = " + bodyValue);
    }

    // @Test
    public void userTest()
    throws Exception
    {
        String id = "test1238";
        String password = "test1238";
        Map<String, Object> dto = new HashMap();

        dto.put("id", id);
        dto.put("password", password);

        String request = objectMapper.writeValueAsString(dto);
        ResultActions result = mockMvc.perform(
                post("/api/v1/service")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
                .header("token", "")
                .header("call_url", "/api/user/login")
                .header("call_method", "POST")
            );
        String resultStr = result.andReturn().getResponse().getContentAsString();
        ObjectMapper objMapper = new ObjectMapper();
        JsonNode rootNode = objMapper.readTree(resultStr);
        String bodyValue = rootNode.path("Body").toString();
        log.info("Body = " + bodyValue);
    }
}
