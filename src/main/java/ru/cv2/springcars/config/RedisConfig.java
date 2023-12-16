package ru.cv2.springcars.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisConfig {
    @Value("${redis.host}")
    private String redisHost;
    @Value("${redis.port}")
    private int redisPort;
//    @Bean
//    public LettuceConnectionFactory redisConnectionFactory() {
//        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration(redisHost, redisPort);
//
//        return new LettuceConnectionFactory(configuration);
//    }

//    @Bean
//    public RedisCacheManager cacheManager() {
//        RedisCacheConfiguration cacheConfig = myDefaultCacheConfig(Duration.ofMinutes(10)).disableCachingNullValues();
//        var jacksonSerializer = new GenericJackson2JsonRedisSerializer(objectMapper());
//        var valueSerializer = RedisSerializationContext.SerializationPair.fromSerializer(jacksonSerializer);
//        return RedisCacheManager.builder(redisConnectionFactory())
//                .cacheDefaults(cacheConfig)
//                .withCacheConfiguration("brands", myDefaultCacheConfig(Duration.ofMinutes(10)).serializeValuesWith(valueSerializer))
//                .build();
//    }
//
//
//
//    private RedisCacheConfiguration myDefaultCacheConfig(Duration duration) {
//        return RedisCacheConfiguration
//                .defaultCacheConfig()
//                .entryTtl(duration)
//                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
//    }
//@Bean
//public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
//    var jacksonSerializer = new GenericJackson2JsonRedisSerializer(objectMapper());
//
//    var valueSerializer = RedisSerializationContext.SerializationPair.fromSerializer(jacksonSerializer);
//    return (builder) -> builder
//            .withCacheConfiguration("brands",
//                    RedisCacheConfiguration.defaultCacheConfig()
//                            .serializeValuesWith(valueSerializer)
//                            .entryTtl(Duration.ofMinutes(20)))
//            .withCacheConfiguration("offers",
//                    RedisCacheConfiguration.defaultCacheConfig()
//                            .serializeValuesWith(valueSerializer)
//                            .entryTtl(Duration.ofMinutes(20)))
//            .withCacheConfiguration("users",
//                    RedisCacheConfiguration.defaultCacheConfig()
//                            .serializeValuesWith(valueSerializer)
//                            .entryTtl(Duration.ofMinutes(20)))
//            .withCacheConfiguration("models",
//                    RedisCacheConfiguration.defaultCacheConfig()
//                            .serializeValuesWith(valueSerializer)
//                            .entryTtl(Duration.ofMinutes(20)));
//}

    @Bean
    public ObjectMapper objectMapper() {
        JavaTimeModule javaTimeModule = new JavaTimeModule();
//        return JsonMapper.builder()
//                .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS, true)
//                .configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE, false)
//                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
//                .configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true)
//                .addModule(javaTimeModule)
//                .findAndAddModules()
//                .build();
        return new ObjectMapper()
                .registerModule(javaTimeModule)
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
                .findAndRegisterModules();
    }
}