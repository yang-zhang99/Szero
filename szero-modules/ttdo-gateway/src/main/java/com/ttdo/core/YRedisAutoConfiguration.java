package com.ttdo.core;


import com.ttdo.core.redis.RedisHelper;
import com.ttdo.core.redis.YRedisProperties;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@ConditionalOnClass(
        name = {"org.springframework.data.redis.connection.RedisConnectionFactory"}
)
@EnableConfigurationProperties(YRedisProperties.class)
@AutoConfigureBefore(RedisAutoConfiguration.class)
public class YRedisAutoConfiguration {


    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate();
        this.buildRedisTemplate(redisTemplate, redisConnectionFactory);
        return redisTemplate;
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        StringRedisTemplate redisTemplate = new StringRedisTemplate();
        this.buildRedisTemplate(redisTemplate, redisConnectionFactory);
        return redisTemplate;
    }

    private void buildRedisTemplate(RedisTemplate<String, String> redisTemplate, RedisConnectionFactory redisConnectionFactory) {
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setStringSerializer(stringRedisSerializer);
        redisTemplate.setDefaultSerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setHashValueSerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(stringRedisSerializer);
        redisTemplate.setConnectionFactory(redisConnectionFactory);
    }

    @Bean
    public HashOperations<String, String, String> hashOperations(StringRedisTemplate redisTemplate) {
        return redisTemplate.opsForHash();
    }

    @Bean
    public ValueOperations<String, String> valueOperations(StringRedisTemplate redisTemplate) {
        return redisTemplate.opsForValue();
    }

    @Bean
    public ListOperations<String, String> listOperations(StringRedisTemplate redisTemplate) {
        return redisTemplate.opsForList();
    }

    @Bean
    public SetOperations<String, String> setOperations(StringRedisTemplate redisTemplate) {
        return redisTemplate.opsForSet();
    }

    @Bean
    public ZSetOperations<String, String> zSetOperations(StringRedisTemplate redisTemplate) {
        return redisTemplate.opsForZSet();
    }


    @Bean
    @ConditionalOnProperty(
            prefix = "y.redis",
            name = {"dynamic-database"},
            havingValue = "false"
    )
    public RedisHelper redisHelper(YRedisProperties redisProperties) {
        return new RedisHelper();
    }
//
//    @Bean
//    @ConditionalOnMissingBean({RedisMessageSource.class})
//    public MessageSource messageSource(RedisHelper redisHelper) {
//        ReloadableResourceBundleMessageSource parentMessageSource = new ReloadableResourceBundleMessageSource();
//        parentMessageSource.setBasenames(MessageAccessor.getBasenames());
//        parentMessageSource.setDefaultEncoding(Charsets.UTF_8.displayName());
//        RedisMessageSource messageSource = new RedisMessageSource(redisHelper);
//        messageSource.setParentMessageSource(parentMessageSource);
//        return messageSource;
//    }
//
//    @Bean
//    @ConditionalOnProperty(
//            prefix = "hzero.cache-value",
//            name = {"enable"},
//            havingValue = "true"
//    )
//    public CacheValueAspect cacheValueAspect(RedisHelper redisHelper, Environment environment) {
//        return new CacheValueAspect(redisHelper, environment);
//    }
//
//    @Bean
//    @ConditionalOnMissingBean
//    public RedisQueueHelper redisQueueHelper(RedisHelper redisHelper, RedisProperties redisProperties) {
//        return new RedisQueueHelper(redisHelper, redisProperties);
//    }
//
//    @Bean
//    @ConditionalOnMissingBean
//    public HandlerInit handlerInit(RedisQueueHelper redisQueueHelper, RedisProperties redisProperties) {
//        return new HandlerInit(redisQueueHelper, redisProperties);
//    }
//
//    @Configuration
//    @ConditionalOnProperty(
//            prefix = "hzero.redis",
//            name = {"dynamic-database"},
//            havingValue = "true",
//            matchIfMissing = true
//    )
//    protected static class DynamicRedisAutoConfiguration {
//        protected DynamicRedisAutoConfiguration() {
//        }
//
//        @Bean
//        public RedisHelper redisHelper(StringRedisTemplate redisTemplate, RedisProperties redisProperties, RedisSentinelConfiguration sentinelConfiguration, RedisClusterConfiguration clusterConfiguration, List<JedisClientConfigurationBuilderCustomizer> jedisBuilderCustomizers, List<LettuceClientConfigurationBuilderCustomizer> lettuceBuilderCustomizers) {
//            DynamicRedisTemplateFactory<String, String> dynamicRedisTemplateFactory = new DynamicRedisTemplateFactory(redisProperties, sentinelConfiguration, clusterConfiguration, jedisBuilderCustomizers, lettuceBuilderCustomizers);
//            DynamicRedisTemplate<String, String> dynamicRedisTemplate = new DynamicRedisTemplate(dynamicRedisTemplateFactory);
//            dynamicRedisTemplate.setDefaultRedisTemplate(redisTemplate);
//            Map<Object, RedisTemplate<String, String>> map = new HashMap(8);
//            map.put(redisProperties.getDatabase(), redisTemplate);
//            dynamicRedisTemplate.setRedisTemplates(map);
//            return new DynamicRedisHelper(dynamicRedisTemplate);
//        }
//    }

}
