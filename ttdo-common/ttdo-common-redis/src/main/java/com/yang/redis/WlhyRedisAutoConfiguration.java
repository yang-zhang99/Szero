package com.yang.redis;


import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.StringRedisSerializer;


/**
 * todo 动态数据源切换未做
 */
@Configuration
@ConditionalOnClass(
        name = {"org.springframework.data.redis.connection.RedisConnectionFactory"}
)
@EnableConfigurationProperties({WlhyRedisProperties.class, CacheProperties.class})
@AutoConfigureBefore(RedisAutoConfiguration.class)
public class WlhyRedisAutoConfiguration {

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

    /**
     * 设置序列化程序
     *
     * @param redisTemplate          redisTemplate
     * @param redisConnectionFactory redisConnectionFactory
     */
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

    public RedisHelper redisHelper(WlhyRedisProperties redisProperties) {
        return new RedisHelper();
    }

    @Bean
    @ConditionalOnMissingBean
    public RedisQueueHelper redisQueueHelper(RedisHelper redisHelper, WlhyRedisProperties redisProperties) {
        return new RedisQueueHelper(redisHelper, redisProperties);
    }

//    @Bean
//    @ConditionalOnMissingBean
//    public HandlerInit handlerInit(RedisQueueHelper redisQueueHelper, WlhyRedisProperties redisProperties) {
//        return new HandlerInit(redisQueueHelper, redisProperties);
//    }

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
//        public RedisHelper redisHelper(StringRedisTemplate redisTemplate, RedisProperties redisProperties, ObjectProvider<RedisSentinelConfiguration> sentinelConfiguration, ObjectProvider<RedisClusterConfiguration> clusterConfiguration, ObjectProvider<List<JedisClientConfigurationBuilderCustomizer>> jedisBuilderCustomizers, ObjectProvider<List<LettuceClientConfigurationBuilderCustomizer>> lettuceBuilderCustomizers) {
//            DynamicRedisTemplateFactory<String, String> dynamicRedisTemplateFactory = new DynamicRedisTemplateFactory(redisProperties, sentinelConfiguration, clusterConfiguration, jedisBuilderCustomizers, lettuceBuilderCustomizers);
//            DynamicRedisTemplate<String, String> dynamicRedisTemplate = new DynamicRedisTemplate(dynamicRedisTemplateFactory);
//            dynamicRedisTemplate.setDefaultRedisTemplate(redisTemplate);
//            Map<Object, RedisTemplate<String, String>> map = new HashMap(8);
//            map.put(redisProperties.getDatabase(), redisTemplate);
//            dynamicRedisTemplate.setRedisTemplates(map);
//            return new DynamicRedisHelper(dynamicRedisTemplate);
//        }
//    }
//    @ConditionalOnProperty(
//            prefix = "wlhy.redis",
//            name = {"dynamic-database"},
//            havingValue = "false"
//    )

}
