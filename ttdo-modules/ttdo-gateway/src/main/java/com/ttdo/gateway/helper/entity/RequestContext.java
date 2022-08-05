package com.ttdo.gateway.helper.entity;


import com.yang.core.oauth.CustomUserDetails;

public class RequestContext {


    private static final ThreadLocal<RequestContext> CONTEXT_THREAD_LOCAL = new ThreadLocal<>();


    public static RequestContext initRequestContext(CheckRequest request, CheckResponse response) {
        RequestContext requestContext = new RequestContext(request, response);
        CONTEXT_THREAD_LOCAL.set(requestContext);
        return requestContext;
    }


    public final CheckRequest request;

    public final CheckResponse response;

    /**
     * Servlet 的 Request 请求
     */
    private Object servletRequest;


    /**
     * 用户的个性化信息
     */
    private CustomUserDetails customUserDetails;


    private CommonRoute route;

    private PermissionDO permissionDO;

    public CommonRoute getRoute() {
        return route;
    }

    public void setRoute(CommonRoute route) {
        this.route = route;
    }

    public PermissionDO getPermissionDO() {
        return permissionDO;
    }

    public void setPermissionDO(PermissionDO permissionDO) {
        this.permissionDO = permissionDO;
    }

    public RequestContext(CheckRequest request, CheckResponse response) {
        this.request = request;
        this.response = response;
    }

    public Object getServletRequest() {
        return servletRequest;
    }

    public void setServletRequest(Object servletRequest) {
        this.servletRequest = servletRequest;
    }

    public CustomUserDetails getCustomUserDetails() {
        return customUserDetails;
    }

    public void setCustomUserDetails(CustomUserDetails customUserDetails) {
        this.customUserDetails = customUserDetails;
    }
}
