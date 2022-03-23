package com.szero.lock.autoconfigure;



import com.szero.lock.LockAspectHandler;
import com.szero.lock.LockInfoProvider;
import com.szero.lock.configure.LockConfigProperties;
import com.szero.lock.factory.LockServiceFactory;
import com.szero.lock.service.impl.*;
import io.micrometer.core.instrument.util.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;

/**
 * @author yang99
 * 自动装配 分布式锁
 */
@Configuration
@AutoConfigureAfter({RedisAutoConfiguration.class})
@EnableConfigurationProperties(LockConfigProperties.class)
@Import({LockAspectHandler.class})
public class LockAutoConfiguration {

    @Autowired
    private LockConfigProperties lockConfig;

    @Bean(
            name = "lockRedissonClient",
            destroyMethod = "shutdown"
    )
    @ConditionalOnMissingBean
    public RedissonClient redissonClient() {
        // 系统目前只是单节点，所以这里也偷个懒，写单节点模式即可，后续如果生产环境如果切换集群模式等，则新增调整。
        Config config = new Config();
        config.useSingleServer().setAddress("redis://" + lockConfig.getHost() + ":" + lockConfig.getPort());
        SingleServerConfig singleServerConfig = config.useSingleServer();
        singleServerConfig.setDatabase(lockConfig.getDatabase());
        if (StringUtils.isNotEmpty(lockConfig.getPassword())) {
            singleServerConfig.setPassword(lockConfig.getPassword());
        }
        singleServerConfig.setIdleConnectionTimeout(lockConfig.getTimeout());
        return Redisson.create(config);
    }

    @Bean
    public LockInfoProvider lockInfoProvider() {
        return new LockInfoProvider();
    }

    @Bean
    public LockServiceFactory lockFactory() {
        return new LockServiceFactory();
    }

    @Bean
    @Scope("prototype")
    @DependsOn("lockRedissonClient")
    public FairLockServiceImpl fairLockServiceImpl() {
        return new FairLockServiceImpl();
    }

    @Bean
    @Scope("prototype")
    public WriteLockServiceImpl writeLockServiceImpl() {
        return new WriteLockServiceImpl();
    }

    @Bean
    @Scope("prototype")
    public MultiLockServiceImpl multiLockService() {
        return new MultiLockServiceImpl();
    }

    @Bean
    @Scope("prototype")
    public ReadLockServiceImpl readLockService() {
        return new ReadLockServiceImpl();
    }

    @Bean
    @Scope("prototype")
    public RedLockServiceImpl redLockService() {
        return new RedLockServiceImpl();
    }

    @Bean
    @Scope("prototype")
    public ReentrantLockServiceImpl reentrantLockService() {
        return new ReentrantLockServiceImpl();
    }

}
