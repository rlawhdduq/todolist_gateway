package todolist.gateway.filter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
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
    {
        // 토큰 검증은 회원가입, 로그인, 토큰발급을 제외한 모든것에서 진행되어야 한다
        log.info("토큰검증 시작");
        String token = request.getHeader("token");
        String url = request.getHeader("call_url");
        String[] exceptUrl = {"/api/user/join", "/api/user/login", "/api/token"};
        if( !Arrays.asList(exceptUrl).contains(url) )
        {
            log.info("토큰검증 진행시작");
            Claims claims = Jwts.parser()
                                .verifyWith(secretKey)
                                .build()
                                .parseSignedClaims(token)
                                .getPayload();
            if (
                claims.getExpiration().before(java.util.Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))
                )
            {
                log.info("토큰만료");
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