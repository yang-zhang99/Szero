package com.ttdo.gateway.helper.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ttdo.gateway.helper.api.HelperFilter;
import com.ttdo.gateway.helper.entity.CheckState;
import com.ttdo.gateway.helper.entity.RequestContext;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.Signer;
import org.springframework.stereotype.Component;


@Component
public class AddJwtFilter implements HelperFilter {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private Signer jwtSigner;

    public AddJwtFilter(Signer jwtSigner) {
        this.jwtSigner = jwtSigner;
    }

    public int filterOrder() {
        return 50;
    }

    public boolean shouldFilter(RequestContext context) {
        return true;
    }

    public boolean run(RequestContext context) {
        try {
            String token = this.objectMapper.writeValueAsString(context.getCustomUserDetails());
            String jwt = "Bearer " + JwtHelper.encode(token, this.jwtSigner).getEncoded();
            context.response.setJwt(jwt);
            return true;
        } catch (JsonProcessingException var4) {
            context.response.setStatus(CheckState.EXCEPTION_GATEWAY_HELPER);
            context.response.setMessage("gateway helper error happened: " + var4.toString());
            return false;
        }
    }
}
