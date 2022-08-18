package com.me.gateway.helper.filter;

import com.me.gateway.helper.api.HelperFilter;
import com.me.gateway.helper.config.GatewayHelperProperties;
import com.me.gateway.helper.domain.entity.CheckState;
import com.me.gateway.helper.domain.entity.RequestContext;
import com.me.gateway.helper.service.SignatureService;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.util.Optional;

public class SignatureAccessFilter implements HelperFilter {
    private final GatewayHelperProperties properties;
    private final SignatureService signatureService;
    private final boolean enabled;
    private final String signLabel;

    public SignatureAccessFilter(GatewayHelperProperties properties, @Autowired(required = false) SignatureService signatureService) {
        this.properties = properties;
        if (properties.getSignature().isEnabled()) {
            Assert.notNull(signatureService, "No qualifying bean of type 'org.hzero.gateway.helper.service.SignatureService' available.");
        }

        this.signatureService = signatureService;
        this.enabled = properties.getSignature().isEnabled();
        this.signLabel = properties.getSignature().getSignLabel();
    }

    public int filterOrder() {
        return 25;
    }

    public boolean shouldFilter(RequestContext context) {
        return this.enabled && (BooleanUtils.isTrue(context.getPermission().getSignAccess()) || ArrayUtils.contains(((String) Optional.ofNullable(context.getPermission().getTag()).orElse("")).split(","), this.signLabel));
    }

    public boolean run(RequestContext context) {
        boolean pass = this.signatureService.verifySignature(context);
        if (pass) {
            context.response.setStatus(CheckState.SUCCESS_SIGNATURE_ACCESS);
            context.response.setMessage("Have access to this 'signAccess' interface, permission: " + context.getPermission());
        } else {
            context.response.setStatus(CheckState.PERMISSION_NOT_PASS_SIGNATURE);
            context.response.setMessage("No access to this 'signAccess' interface, permission: " + context.getPermission());
        }

        return false;
    }
}
