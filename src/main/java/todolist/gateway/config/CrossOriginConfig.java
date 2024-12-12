package todolist.gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CrossOriginConfig implements WebMvcConfigurer{

    @Value("${allow.host.url}")
    private String allowHostUrl;

    @Override
    public void addCorsMappings(CorsRegistry registry)
    {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("POST", "GET", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("Content-Type", "call_url", "call_method", "token")
                .allowCredentials(true);

        // registry.addMapping("/ws")
        //         .allowedOriginPatterns("*")
        //         .allowedMethods("POST", "GET", "OPTIONS")
        //         .allowedHeaders("Content-Type", "call_url", "call_method", "token")
        //         .allowCredentials(true);
    }
}
