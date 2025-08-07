// package todolist.gateway.config;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.data.redis.connection.RedisConnectionFactory;
// import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
// import org.springframework.data.redis.core.RedisTemplate;
// import org.springframework.data.redis.listener.PatternTopic;
// import org.springframework.data.redis.listener.RedisMessageListenerContainer;
// import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
// import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
// import org.springframework.data.redis.serializer.StringRedisSerializer;

// import todolist.gateway.component.RedisMessageListener;

// @Configuration
// public class RedisConfig {
//     // @Autowired
//     // private RedisConnectionFactory redisConnectionFactory;

//     @Value("${spring.redis.data.host}")
//     private String redisHost;
//     @Value("${spring.redis.data.port}")
//     private int redisPort;

//     @Autowired
//     private RedisMessageListener redisMessageListener;

//     @Bean
//     public RedisConnectionFactory redisConnectionFactory()
//     {
//         LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(redisHost, redisPort);
//         return lettuceConnectionFactory;
//     }

//     @Bean
//     public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory)
//     {
//         RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
//         redisTemplate.setConnectionFactory(redisConnectionFactory);
//         redisTemplate.setKeySerializer(new StringRedisSerializer()); // 키는 문자열로 직렬화시킴
//         redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer()); // 값은 JSON 직렬화
//         return redisTemplate;
//     }

//     @Bean
//     public RedisMessageListenerContainer container()
//     {
//         RedisMessageListenerContainer container = new RedisMessageListenerContainer();
//         container.setConnectionFactory(redisConnectionFactory());
//         container.addMessageListener(new MessageListenerAdapter(redisMessageListener), new PatternTopic("board/all"));
//         container.addMessageListener(new MessageListenerAdapter(redisMessageListener), new PatternTopic("board/friends:*"));
//         container.addMessageListener(new MessageListenerAdapter(redisMessageListener), new PatternTopic("board/community:*"));
//         return container;
//     }
// }

// 비동기 메시지 큐 방식(카프카, 레디스 사용)에서 RestApi 방식으로 구현방향을 변경함에 따라 기존에 사용하던 의존성들을 주석처리한다.
// 비동기 메시지 큐 방식은 RestApi 방식으로 구현을 마친 후 성능개선을 위해 해당방식으로 변경할 때 다시 주석을 풀고 사용하자.