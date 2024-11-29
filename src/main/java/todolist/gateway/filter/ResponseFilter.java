package todolist.gateway.filter;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class ResponseFilter implements Filter{
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger log = LoggerFactory.getLogger(ResponseFilter.class);
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
    throws IOException, ServletException
    {
        // !! 이 필터는... 고려를 좀 더 해보자 지금 응답처리할 때 뭔가 잘못세팅돼서 특정요청에 대해선 응답이 끊기는 현상이 발생한다 !!

        // HttpServletResponse httpResponse = (HttpServletResponse) response;
        // ResponseWrapper responseWrapper = new ResponseWrapper(httpResponse);
        
        // chain.doFilter(request, responseWrapper);
        chain.doFilter(request, response);

        // byte[] originResponse = responseWrapper.getResponseData();
        // String originalResponse = new String(originResponse, StandardCharsets.UTF_8);

        // String modifiedResponse = "{}";
        // // log.info("검증 시작");
        // if( !originalResponse.isEmpty() )
        // {
        //     // log.info("데이터 있음");
        //     // log.info("data = " + originalResponse);
        //     if(isValidJson(originalResponse))
        //     {
        //         // log.info("String Object타입");
        //         Object objectResponse = objectMapper.readValue(originalResponse, Object.class);
        //         modifiedResponse = "{ \"Body\": " + objectMapper.writeValueAsString(objectResponse) + " }";
        //         // log.info("response = " + modifiedResponse);
        //     }
        //     else
        //     {
        //         // log.info("String 형식임");
        //         modifiedResponse = "{ \"Body\": \"" + originalResponse + "\" }";
        //         // log.info("response = " + modifiedResponse);
        //     }
        // }
        // log.info("응답 전");
        // httpResponse.setContentType("application/json");
        // httpResponse.setContentLength(modifiedResponse.getBytes(StandardCharsets.UTF_8).length);
        // log.info("response Content 설정 완료");
        // OutputStream out = httpResponse.getOutputStream();
        // log.info("스트림 열기 완료");
        // out.write(modifiedResponse.getBytes());
        // log.info("스트림에 작성 완료");
        // out.flush();
        // log.info("스트림 플러쉬");
    }

    private Boolean isValidJson(String str)
    {
        str = str.trim();
        return (str.startsWith("{") && str.endsWith("}")) || (str.startsWith("[{") && str.endsWith("}]"));
    }

    @Override
    public void destroy(){}
}
