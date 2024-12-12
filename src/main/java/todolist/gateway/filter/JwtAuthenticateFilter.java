package todolist.gateway.filter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@CrossOrigin( origins = "http://front:80/api/v1/service" )
public class JwtAuthenticateFilter extends OncePerRequestFilter{

    @Value("${jwt.secret.lawSecretKey}")
    private String lawSecretKey;
    
    private SecretKey secretKey;

    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticateFilter.class);

    @PostConstruct
    private void initSecretKey()
    {
        if( lawSecretKey == null || lawSecretKey.isEmpty() ) throw new IllegalArgumentException("JWT secret key is not configured.");
        this.secretKey = Keys.hmacShaKeyFor(lawSecretKey.getBytes());
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
    throws IOException, ServletException
    {
        // OPTIONS 요청을 처리
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            // log.info("옵션 하이~");
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            response.setHeader("Access-Control-Allow-Headers", "Content-Type, call_url, call_method, token");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        // WebSocket 요청을 처리
        if(request.getRequestURI().startsWith("/ws"))
        {
            log.info("웹소켓 요청 :" + request.getRequestURI());
            filterChain.doFilter(request, response);
            return;
        }
        
        /*
         * 데이터 찍어보기용 소스
         TestWrapper wrapperRequest = new TestWrapper(request);
         StringBuilder requestBody = new StringBuilder();
         BufferedReader reader = new BufferedReader(new InputStreamReader(wrapperRequest.getInputStream()));
         String line;
         while ((line = reader.readLine()) != null) {
             requestBody.append(line);
         }
         reader.close();
 
         log.info("RequestBody = " + requestBody.toString());
         */

        // 토큰 검증은 회원가입, 로그인, 토큰발급을 제외한 모든것에서 진행되어야 한다
        // log.info("토큰검증 시작");
        String token = request.getHeader("token");
        String url = request.getHeader("call_url");
        String[] exceptUrl = {"/api/user/join", "/api/user/login", "/api/token", "/ws"};
        log.info("callUrl : "+url);
        if( !Arrays.asList(exceptUrl).contains(url) )
        {
            // log.info("토큰검증 진행시작");
            Claims claims = Jwts.parser()
                                .verifyWith(secretKey)
                                .build()
                                .parseSignedClaims(token)
                                .getPayload();
            if (
                claims.getExpiration().before(java.util.Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))
                )
            {
                // log.info("토큰만료");
                throw new JwtException("Token has Expired");
            }
                                
        }

        try 
        {
            filterChain.doFilter(request, response);
        }
        catch(ServletException e)
        {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        catch(IOException e)
        {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
    }
}
