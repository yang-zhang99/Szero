package com.me.gateway.ratelimit;

import org.springframework.context.annotation.Configuration;

@Configuration
public class RateLimitConfiguration {

//    @Bean
//    public ModeSwitcher modeSwitcher(){
//        return new DoubleModeSwitcher();
//    }
//
//    @Bean
//    public OriginKeyResolver originKeyResolver(){
//        return new OriginKeyResolver();
//    }
//
//    @Bean
//    public TenantKeyResolver tenantKeyResolver(){
//        return new TenantKeyResolver();
//    }
//
//    @Bean
//    public UrlKeyResolver urlKeyResolver(){
//        return new UrlKeyResolver();
//    }
//
//    @Bean
//    public UserKeyResolver userKeyResolver(){
//        return new UserKeyResolver();
//    }
//
//    @Bean
//    public RoleKeyResolver roleKeyResolver(){
//        return new RoleKeyResolver();
//    }
//
//    @Primary
//    @Bean
//    public EnhancedRedisRateLimiter enhancedRedisRateLimiter(ReactiveRedisTemplate<String, String> redisTemplate,
//                                                             @Qualifier(RedisRateLimiter.REDIS_SCRIPT_NAME) RedisScript<List<Long>> redisScript,
//                                                             Validator validator) {
//        return new EnhancedRedisRateLimiter(redisTemplate, redisScript, validator);
//    }

}
