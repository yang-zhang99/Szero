package com.me.gateway.helper.api;


import com.me.gateway.helper.domain.entity.RequestContext;

/**
 * 鉴权过滤器，可以提供实现接口加入自定义的鉴权方式
 */
public interface HelperFilter {


    /**
     * filter 顺序，越小越先执行
     *
     * @return filter 顺序
     */
    int filterOrder();


    /**
     * 是否执行
     *
     * @param requestContext
     * @return true 执行
     */
    boolean shouldFilter(RequestContext requestContext);


    /**
     * 执行方法
     *
     * @param context
     * @return true 则继续执行
     */
    boolean run(RequestContext context);
}
