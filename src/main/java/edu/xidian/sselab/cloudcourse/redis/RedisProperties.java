package edu.xidian.sselab.cloudcourse.redis;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Component;

@Data
@Component
@Configuration
public class RedisProperties {
    @Value("${redis.node}")
    private String redisNode;

    @Value("${redis.key}")
    private String redisKey;

    // solve @Value cannot resolve ${}
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    public String getRedisNode() {
        return redisNode;
    }

    public String getRedisKey() {
        return redisKey;
    }
}
