package com.me.oauth.api.controller.v1;


import com.me.oauth.security.custom.CustomRedisTokenStore;
import com.yang.core.base.BaseConstants;
import com.yang.core.oauth.CustomUserDetails;
import com.yang.core.oauth.DetailsHelper;
import com.yang.core.util.Results;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

//@Api(tags = "Public Api")
@RestController("v1.publicController")
@RequestMapping("/public")
public class PublicController {

    private CustomRedisTokenStore tokenStore;

    private static final String FIELD_KICKOFF = "kickoff";

    public PublicController(TokenStore tokenStore) {
        this.tokenStore = (CustomRedisTokenStore) tokenStore;
    }

//    @ApiOperation("判断access_token是否已下线")
//    @Permission(permissionPublic = true)
    @PostMapping("/token/kickoff")
    public ResponseEntity<Map<String, Object>> tokenOffline(@RequestParam("access_token") String accessToken){
        boolean exists = tokenStore.existsOfflineAccessToken(accessToken);
        Map<String, Object> result = new HashMap<>(2);
        result.put(FIELD_KICKOFF, exists ? BaseConstants.Flag.YES : BaseConstants.Flag.NO);
        return Results.success(result);
    }

//    @ApiOperation("判断当前用户是否在线")
//    @Permission(permissionPublic = true)
    @GetMapping(value = "/is-online")
    public ResponseEntity<Boolean> isLogin() {
        CustomUserDetails userDetails = DetailsHelper.getUserDetails();
        return Results.success(userDetails != null);
    }

}
