package todolist.gateway.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CrossOriginConfig implements WebMvcConfigurer{
    
    @Override
    public void addCorsMappings(CorsRegistry registry)
    {
        registry.addMapping("/api/v1/service")
                .allowedOriginPatterns("http://localhost:8989")
                .allowedMethods("POST", "GET", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("Content-Type", "call_url", "call_method", "token")
                .allowCredentials(true);
    }
}
