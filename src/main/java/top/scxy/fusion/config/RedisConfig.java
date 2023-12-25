package top.scxy.fusion.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
    // RedisTemplate配置
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory); // 设置连接工厂
        redisTemplate.setKeySerializer(new StringRedisSerializer()); // 设置key序列化
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer()); // 设置value序列化
        return redisTemplate;
    }
}
