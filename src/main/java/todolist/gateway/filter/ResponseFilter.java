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
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        ResponseWrapper responseWrapper = new ResponseWrapper(httpResponse);
        
        chain.doFilter(request, responseWrapper);

        byte[] originResponse = responseWrapper.getResponseData();
        String originalResponse = new String(originResponse, StandardCharsets.UTF_8);

        String modifiedResponse = "{}";
        log.info("검증 시작");
        if( !originalResponse.isEmpty() )
        {
            log.info("데이터 있음");
            log.info("data = " + originalResponse);
            if(isValidJson(originalResponse))
            {
                log.info("String Object타입");
                Object objectResponse = objectMapper.readValue(originalResponse, Object.class);
                modifiedResponse = "{ \"Body\": " + objectMapper.writeValueAsString(objectResponse) + " }";
                log.info("response = " + modifiedResponse);
            }
            else
            {
                log.info("String 형식임");
                modifiedResponse = "{ \"Body\": \"" + originalResponse + "\" }";
                log.info("response = " + modifiedResponse);
            }
        }
        httpResponse.setContentType("application/json");
        httpResponse.setContentLength(modifiedResponse.getBytes(StandardCharsets.UTF_8).length);
        OutputStream out = httpResponse.getOutputStream();
        out.write(modifiedResponse.getBytes());
        out.flush();
    }

    private Boolean isValidJson(String str)
    {
        str = str.trim();
        return (str.startsWith("{") && str.endsWith("}")) || (str.startsWith("[{]") && str.endsWith("]"));
    }

    @Override
    public void destroy(){}
}
