package com.ttdo.autoconfigure.gateway;

import com.ttdo.core.redis.RedisHelper;
import com.ttdo.gateway.filter.IpCheckedFilter;
import com.ttdo.gateway.filter.RedisBlackSetRepository;
import com.ttdo.gateway.filter.RedisWhiteSetRepository;
import com.ttdo.gateway.filter.metric.RequestCountRules;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConditionAutoConfiguration {

    //======= metric start
    @Bean
    @ConditionalOnMissingBean
    public RequestCountRules requestCountRules(RedisHelper redisHelper) {
        RedisWhiteSetRepository redisWhiteSetRepository = new RedisWhiteSetRepository(redisHelper);
        RedisBlackSetRepository redisBlackSetRepository = new RedisBlackSetRepository(redisHelper);
        return new RequestCountRules(redisHelper, redisWhiteSetRepository, redisBlackSetRepository);
    }

    @Bean
    public IpCheckedFilter ipCheckedFilter(RequestCountRules requestCountRules) {
        return new IpCheckedFilter(requestCountRules);
    }

}
