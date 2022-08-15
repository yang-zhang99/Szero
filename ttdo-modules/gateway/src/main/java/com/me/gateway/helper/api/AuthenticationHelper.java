package com.me.gateway.helper.api;

import com.me.gateway.helper.entity.ResponseContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * @author XCXCXCXCX
 * @since 1.0
 */
public interface AuthenticationHelper {
    /**
     * 鉴权入口
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse 不要对 response 做任何关闭处理
     * @return 鉴权信息
     */
    ResponseContext authentication(HttpServletRequest request, HttpServletResponse response) throws IOException;

}
