//package com.ttdo.gateway.helper.filter;
//
//import com.ttdo.gateway.helper.api.HelperFilter;
//import com.ttdo.gateway.helper.entity.CheckState;
//import com.ttdo.gateway.helper.entity.RequestContext;
//import org.springframework.stereotype.Component;
//
//
//@Component
//public class PublicRequestFilter implements HelperFilter {
//
//
//    public int filterOrder() {
//        return 30;
//    }
//
//    public boolean shouldFilter(RequestContext context) {
//        return context.getPermission().getPublicAccess();
//    }
//
//    public boolean run(RequestContext context) {
//        context.response.setStatus(CheckState.SUCCESS_PUBLIC_ACCESS);
//        context.response.setMessage("Have access to this 'publicAccess' interface, permission: " + context.getPermission());
//        return false;
//    }
//}
