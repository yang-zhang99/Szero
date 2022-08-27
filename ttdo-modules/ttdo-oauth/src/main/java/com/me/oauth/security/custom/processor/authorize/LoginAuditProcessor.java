package com.me.oauth.security.custom.processor.authorize;

import com.me.oauth.domain.service.AuditLoginService;
import com.me.core.oauth.DetailsHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 创建 access_token 之后，记录审计信息
 *
 * @author bojiangzhou 2019/11/20
 */
@Component
public class LoginAuditProcessor implements AuthorizeSuccessProcessor {

    @Autowired
    private AuditLoginService auditLoginService;

    @Override
    public Object process(HttpServletRequest request, HttpServletResponse response,
                          AuthorizationRequest authorizationRequest, OAuth2AccessToken accessToken) {
        auditLoginService.addLoginRecord(request, accessToken.getValue(), authorizationRequest.getClientId(), DetailsHelper.getUserDetails());
        return null;
    }

    @Override
    public int getOrder() {
        return 20;
    }
}
