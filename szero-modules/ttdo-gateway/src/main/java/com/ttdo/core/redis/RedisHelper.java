package com.ttdo.core.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;

public class RedisHelper implements InitializingBean {

    private Logger logger = LoggerFactory.getLogger(RedisHelper.class);

    public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private ValueOperations<String, String> valueOpr;
    @Autowired
    private HashOperations<String, String, String> hashOpr;
    @Autowired
    private ListOperations<String, String> listOpr;
    @Autowired
    private SetOperations<String, String> setOpr;
    @Autowired
    private ZSetOperations<String, String> zSetOpr;

    static ObjectMapper objectMapper = new ObjectMapper();
    public static final long DEFAULT_EXPIRE = 86400L;
    public static final long NOT_EXPIRE = -1L;


    @Override

    public void afterPropertiesSet() throws Exception {

    }


    public String strGet(String key) {
        return this.valueOpr.get(key);
    }
}
