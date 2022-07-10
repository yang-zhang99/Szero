//package com.ttdo.gateway.helper.filter;
//
//import com.ttdo.gateway.helper.api.HelperFilter;
//import com.ttdo.gateway.helper.config.GatewayHelperProperties;
//import com.ttdo.gateway.helper.domain.TranceSpan;
//import com.ttdo.gateway.helper.entity.CommonRoute;
//import com.ttdo.gateway.helper.entity.PermissionDO;
//import com.ttdo.gateway.helper.entity.RequestContext;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.stereotype.Component;
//
//import rx.Observable;
//import rx.schedulers.Schedulers;
//
//import java.time.LocalDate;
//import java.util.concurrent.TimeUnit;
//
///**
// * API 接口统计
// */
//@Component
//public class CollectSpanFilter implements HelperFilter {
//
//    // 是否启动 API 统计
//    private boolean collectSpanEnabled;
//    private StringRedisTemplate stringRedisTemplate;
//
//
//    public CollectSpanFilter(GatewayHelperProperties properties, StringRedisTemplate stringRedisTemplate) {
//        this.collectSpanEnabled = properties.getFilter().getCollectSpan().isEnabled();
//        ;
//        this.stringRedisTemplate = stringRedisTemplate;
//    }
//
//    @Override
//    public int filterOrder() {
//        return 25;
//    }
//
//    @Override
//    public boolean shouldFilter(RequestContext requestContext) {
//        return this.collectSpanEnabled;
//    }
//
//    @Override
//    public boolean run(RequestContext context) {
//
//        CommonRoute zuulRoute = context.getRoute();
//
//        String serviceId = zuulRoute.getServiceId();
//        String method = context.request.method;
//        PermissionDO permissionDO = context.getPermissionDO();
//
//
//        TranceSpan tranceSpan = new TranceSpan(permissionDO.getPath(), serviceId, method, LocalDate.now());
//        Observable.just(tranceSpan).subscribeOn(Schedulers.io()).subscribe(this::tranceSpanSubscriber);
//        return true;
//    }
//
//    private void tranceSpanSubscriber(final TranceSpan tranceSpan) {
//        this.staticInvokerCount(tranceSpan.getServiceInvokeKey(), tranceSpan.getServiceInvokeValue());
//        this.staticInvokerCount(tranceSpan.getApiInvokeKey(), tranceSpan.getApiInvokeValue());
//    }
//
//    private void staticInvokerCount(String key, String value) {
//        if (Boolean.TRUE.equals(this.stringRedisTemplate.hasKey(key))) {
//            this.stringRedisTemplate.opsForZSet().incrementScore(key, value, 1.0);
//        } else {
//            this.stringRedisTemplate.opsForZSet().add(key, value, 1.0);
//            this.stringRedisTemplate.expire(key, 31L, TimeUnit.DAYS);
//        }
//    }
//}
