package com.me.oauth.infra.encrypt.impl;

import com.me.oauth.infra.encrypt.EncryptRepository;
import com.me.oauth.security.config.SecurityProperties;
import com.me.common.HZeroService;
import com.me.redis.RedisHelper;
import com.me.redis.safe.SafeRedisHelper;
import org.apache.commons.lang3.StringUtils;

import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;

/**
 * 加密资源库实现
 *
 * @author bojiangzhou 2019/12/05
 */
@Component
public class DefaultEncryptRepository implements EncryptRepository {

    private final RedisHelper redisHelper;
    private final SecurityProperties securityProperties;

    private static final String PUBLIC_KEY = HZeroService.Platform.CODE + ":encrypt:public-key";
    private static final String PRIVATE_KEY = HZeroService.Platform.CODE + ":encrypt:private-key";

    public DefaultEncryptRepository(RedisHelper redisHelper,
                                    SecurityProperties securityProperties) {
        this.redisHelper = redisHelper;
        this.securityProperties = securityProperties;
    }

    @Override
    @Nonnull
    public String getPublicKey() {
        String publicKey = SafeRedisHelper.execute(HZeroService.Platform.REDIS_DB, redisHelper,
                        () -> redisHelper.strGet(PUBLIC_KEY));

        if (StringUtils.isBlank(publicKey)) {
            publicKey = securityProperties.getPassword().getPublicKey();
        }

        return publicKey;
    }

    @Override
    @Nonnull
    public String getPrivateKey() {
        String privateKey = SafeRedisHelper.execute(HZeroService.Platform.REDIS_DB, redisHelper,
                        () -> redisHelper.strGet(PRIVATE_KEY));

        if (StringUtils.isBlank(privateKey)) {
            privateKey = securityProperties.getPassword().getPrivateKey();
        }
        return privateKey;
    }
}
