package com.ttdo.core.message;


import java.text.MessageFormat;
import java.util.Locale;
import java.util.Optional;

import com.ttdo.core.redis.RedisHelper;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;

//public class RedisMessageSource extends AbstractMessageSource implements IMessageSource {
//    private static final Logger LOGGER = LoggerFactory.getLogger(RedisMessageSource.class);
//    private static final String MESSAGE_KEY = "hpfm:message:";
//    private RedisHelper redisHelper;
//    private int redisDb = 1;

//    public RedisMessageSource() {
//        ApplicationContextHelper.asyncInstanceSetter(RedisHelper.class, this, "setRedisHelper");
//        ApplicationContextHelper.asyncInstanceSetter(Environment.class, this, "setEnvironment");
//    }
//
//    public RedisMessageSource(RedisHelper redisHelper) {
//        this.redisHelper = redisHelper;
//        ApplicationContextHelper.asyncInstanceSetter(Environment.class, this, "setEnvironment");
//    }
//
//    private void setRedisHelper(RedisHelper redisHelper) {
//        this.redisHelper = redisHelper;
//    }
//
//    private void setEnvironment(Environment environment) {
//        this.redisDb = Integer.parseInt(environment.getProperty("hzero.service.platform.redis-db", "1"));
//    }
//
//    public void setParent(MessageSource messageSource) {
//        this.setParentMessageSource(messageSource);
//        this.setAlwaysUseMessageFormat(true);
//    }
//
//    public Message resolveMessage(ReloadableResourceBundleMessageSource parentMessageSource, String code, Object[] args, Locale locale) {
//        return this.resolveMessage(parentMessageSource, code, args, (String)null, locale);
//    }
//
//    public Message resolveMessage(ReloadableResourceBundleMessageSource parentMessageSource, String code, Object[] args, String defaultMessage, Locale locale) {
//        Message message = this.getMessageObject(code, locale);
//        String desc = null;
//        if (message != null) {
//            desc = message.desc();
//        } else {
//            try {
//                desc = this.getParentMessageSource().getMessage(code, (Object[])null, locale);
//            } catch (NoSuchMessageException var9) {
//                LOGGER.warn("resolveMessage not found message for code={}", code);
//            }
//        }
//
//        if (StringUtils.isBlank(desc) && StringUtils.isNotBlank(defaultMessage)) {
//            desc = defaultMessage;
//        }
//
//        if (StringUtils.isNotBlank(desc) && ArrayUtils.isNotEmpty(args)) {
//            desc = this.createMessageFormat(desc, locale).format(args);
//        }
//
//        if (StringUtils.isBlank(desc)) {
//            desc = code;
//        }
//
//        message = (Message)Optional.ofNullable(message).map((m) -> {
//            return m.setDesc(desc);
//        }).orElse(new Message(code, desc));
//        LOGGER.debug("resolve message: code={}, message={}, language={}", new Object[]{code, message, locale});
//        return message;
//    }
//
//    protected MessageFormat resolveCode(String code, Locale locale) {
//        Message message = this.getMessageObject(code, locale);
//        String msg = null;
//        if (message != null) {
//            msg = message.desc();
//        } else {
//            try {
//                msg = this.getParentMessageSource().getMessage(code, (Object[])null, locale);
//            } catch (NoSuchMessageException var6) {
//                LOGGER.warn("resolveCode not found message for code={}", code);
//            }
//        }
//
//        return StringUtils.isNotBlank(msg) ? this.createMessageFormat(msg, locale) : null;
//    }
//
//    private Message getMessageObject(String code, Locale locale) {
//        if (StringUtils.isBlank(code)) {
//            return new Message("null", "null");
//        } else if (this.redisHelper == null) {
//            LOGGER.debug("redisHelper is null, use local message");
//            return null;
//        } else {
//            String language;
//            if (locale != null && !StringUtils.isBlank(locale.toString())) {
//                language = locale.toString();
//            } else {
//                language = LanguageHelper.language();
//            }
//
//            String obj = (String)SafeRedisHelper.execute(this.redisDb, () -> {
//                return this.redisHelper.hshGet("hpfm:message:" + language, code);
//            });
//            return StringUtils.isNotEmpty(obj) ? (Message)this.redisHelper.fromJson(obj, Message.class) : null;
//        }
//    }
//}