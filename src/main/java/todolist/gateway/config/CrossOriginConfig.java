package todolist.gateway.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class CrossOriginConfig implements WebMvcConfigurer{

    @Value("${allow.host.url}")
    private String allowHostUrl;
    private static final Logger log = LoggerFactory.getLogger(CrossOriginConfig.class);
    @Override
    public void addCorsMappings(CorsRegistry registry)
    {
        log.info(registry.toString());
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("POST", "GET", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*");
                // .allowCredentials(true);

        // registry.addMapping("/ws")
        //         .allowedOriginPatterns("*")
        //         .allowedMethods("POST", "GET", "OPTIONS")
        //         .allowedHeaders("Content-Type", "call_url", "call_method", "token")
        //         .allowCredentials(true);
    }
}
