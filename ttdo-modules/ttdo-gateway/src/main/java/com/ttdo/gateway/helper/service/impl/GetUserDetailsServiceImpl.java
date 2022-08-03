//package com.ttdo.gateway.helper.service.impl;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.netflix.discovery.DiscoveryClient;
//import com.ttdo.core.oauth.CustomUserDetails;
//import com.ttdo.core.user.UserType;
//import com.ttdo.gateway.helper.domain.vo.CustomUserDetailsWithResult;
//import com.ttdo.gateway.helper.entity.CheckState;
//import com.ttdo.gateway.helper.service.GetUserDetailsService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.cloud.client.ServiceInstance;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.util.Assert;
//import org.springframework.web.client.RestTemplate;
//import reactor.netty.internal.util.MapUtils;
//
//import javax.inject.Qualifier;
//import java.io.IOException;
//import java.util.*;
//import java.util.stream.Collectors;
//
//@Service
//public class GetUserDetailsServiceImpl implements GetUserDetailsService {
//    private static final Logger LOGGER = LoggerFactory.getLogger(GetUserDetailsService.class);
//    private static final String PRINCIPAL = "principal";
//    private static final String OAUTH2REQUEST = "oauth2Request";
//    private static final String ADDITION_INFO = "additionInfo";
//    private static final String ADDITION_INFO_MEANING = "additionInfoMeaning";
//    private static final String USER_ID = "userId";
//    private static final String ANONYMOUS_LANGUAGE = "zh_CN";
//    private static final String ANONYMOUS_TIME_ZONE = "GMT+8";
//    private final ObjectMapper objectMapper = new ObjectMapper();
//    private final RestTemplate restTemplate;
//    private final DiscoveryClient discoveryClient;
//    private String oauthUserApi = "http://hzero-oauth/oauth/api/user";
//    private volatile boolean isInit = false;
//
//    public GetUserDetailsServiceImpl(@Qualifier("helperRestTemplate") RestTemplate restTemplate, DiscoveryClient discoveryClient) {
//        this.restTemplate = restTemplate;
//        this.discoveryClient = discoveryClient;
//    }
//
//    public CustomUserDetailsWithResult getUserDetails(String token) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", token);
//        HttpEntity<String> entity = new HttpEntity("", headers);
//
//        try {
//            ResponseEntity<String> responseEntity = this.restTemplate.exchange(this.getOauthUserApi(), HttpMethod.GET, entity, String.class, new Object[0]);
//            if (responseEntity.getStatusCode().is2xxSuccessful()) {
//                CustomUserDetails userDetails = this.extractPrincipal((Map) this.objectMapper.readValue((String) responseEntity.getBody(), Map.class));
//                return new CustomUserDetailsWithResult(userDetails, CheckState.SUCCESS_PASS_SITE);
//            } else {
//                return new CustomUserDetailsWithResult(CheckState.PERMISSION_GET_USE_DETAIL_FAILED, "Get customUserDetails error from [" + this.getOauthUserApi() + "], token: " + token + ", response: " + responseEntity);
//            }
//        } catch (RuntimeException var6) {
//            LOGGER.warn("Get customUserDetails error from hzero-oauth, token: {}", token, var6);
//            return new CustomUserDetailsWithResult(CheckState.PERMISSION_ACCESS_TOKEN_EXPIRED, "Access_token is expired or invalid, Please re-login and set correct access_token by HTTP header 'Authorization'");
//        } catch (IOException var7) {
//            return new CustomUserDetailsWithResult(CheckState.EXCEPTION_GATEWAY_HELPER, "Gateway helper error happened: " + var7.toString());
//        }
//    }
//
//    private String getOauthUserApi() {
//        if (this.isInit) {
//            return this.oauthUserApi;
//        } else {
//            synchronized (this) {
//                if (this.isInit) {
//                    return this.oauthUserApi;
//                }
//
//                boolean instanceUp = false;
////                String name = HZeroService.getRealName("${hzero.service.oauth.name:hzero-oauth}");
////                ServiceInstance instance = (ServiceInstance) Optional.ofNullable(this.discoveryClient.getInstances(name)).flatMap((list) -> {
////                    return list.stream().findFirst();
////                }).orElse((Object) null);
////                String context = "";
////                if (instance == null) {
////                    context = "/oauth";
////                } else {
////                    instanceUp = true;
////                    if (MapUtils.isNotEmpty(instance.getMetadata())) {
////                        context = (String) instance.getMetadata().get("CONTEXT");
////                    }
////                }
//
////                this.oauthUserApi = "http://" + name + context + "/api/user";
//                this.isInit = instanceUp;
//            }
//
//            return this.oauthUserApi;
//        }
//    }
//
//    private CustomUserDetails extractPrincipal(Map<String, Object> map) {
//        boolean isClientOnly = false;
//        Map<String, Object> oauth2request = null;
//        if (map.get("oauth2Request") != null) {
//            oauth2request = (Map) map.get("oauth2Request");
//            Assert.notNull(oauth2request.get("grantType"), "grantType not be null.");
//            if ("client_credentials".equals(oauth2request.get("grantType"))) {
//                isClientOnly = true;
//            }
//        }
//
//        if (map.get("principal") != null) {
//            map = (Map) map.get("principal");
//        }
//
//        return this.setUserDetails(map, isClientOnly, oauth2request);
//    }
//
//    private CustomUserDetails setUserDetails(final Map<String, Object> map, boolean isClientOnly, Map<String, Object> oauth2request) {
//        if (map.containsKey("userId")) {
//            String userType = null;
//            if (map.get("userType") != null) {
//                userType = map.get("userType") != null ? map.get("userType").toString() : null;
//            }
//
//            CustomUserDetails user = new CustomUserDetails((String) map.get("username"), "unknown password", UserType.ofDefault(userType).value(), Collections.emptyList());
//            if (map.get("organizationId") != null) {
//                user.setOrganizationId(Long.parseLong(String.valueOf(map.get("organizationId"))));
//            }
//
//            if (map.get("userId") != null) {
//                user.setUserId(Long.parseLong(String.valueOf(map.get("userId"))));
//            }
//
//            if (map.get("language") != null) {
//                user.setLanguage((String) map.get("language"));
//            }
//
//            if (map.get("admin") != null) {
//                user.setAdmin((Boolean) map.get("admin"));
//            }
//
//            if (map.get("timeZone") != null) {
//                user.setTimeZone((String) map.get("timeZone"));
//            }
//
//            if (map.get("email") != null) {
//                user.setEmail((String) map.get("email"));
//            }
//
//            if (map.get("realName") != null) {
//                user.setRealName(String.valueOf(map.get("realName")));
//            }
//
//            if (map.get("roleId") != null) {
//                user.setRoleId(Long.valueOf(String.valueOf(map.get("roleId"))));
//            }
//
//            List roleIds;
//            Object userTenantRoleIds;
//            if (map.get("roleIds") != null) {
//                userTenantRoleIds = map.get("roleIds");
//                if (userTenantRoleIds instanceof List) {
//                    roleIds = (List) userTenantRoleIds;
//                    user.setRoleIds((List) roleIds.stream().map((item) -> {
//                        return Long.valueOf(String.valueOf(item));
//                    }).collect(Collectors.toList()));
//                }
//            }
//
//            if (map.get("tenantId") != null) {
//                user.setTenantId(Long.valueOf(String.valueOf(map.get("tenantId"))));
//            }
//
//            if (map.get("tenantNum") != null) {
//                user.setTenantNum(String.valueOf(map.get("tenantNum")));
//            }
//
//            if (map.get("tenantIds") != null) {
//                userTenantRoleIds = map.get("tenantIds");
//                if (userTenantRoleIds instanceof List) {
//                    roleIds = (List) userTenantRoleIds;
//                    user.setTenantIds((List) roleIds.stream().map((item) -> {
//                        return Long.valueOf(String.valueOf(item));
//                    }).collect(Collectors.toList()));
//                }
//            }
//
//            if (map.get("siteRoleIds") != null) {
//                userTenantRoleIds = map.get("siteRoleIds");
//                if (userTenantRoleIds instanceof List) {
//                    roleIds = (List) userTenantRoleIds;
//                    user.setSiteRoleIds((List) roleIds.stream().map((item) -> {
//                        return Long.valueOf(String.valueOf(item));
//                    }).collect(Collectors.toList()));
//                }
//            }
//
//            if (map.get("tenantRoleIds") != null) {
//                userTenantRoleIds = map.get("tenantRoleIds");
//                if (userTenantRoleIds instanceof List) {
//                    roleIds = (List) userTenantRoleIds;
//                    user.setTenantRoleIds((List) roleIds.stream().map((item) -> {
//                        return Long.valueOf(String.valueOf(item));
//                    }).collect(Collectors.toList()));
//                }
//            }
//
//            if (map.get("roleMergeFlag") != null) {
//                user.setRoleMergeFlag(Boolean.valueOf(String.valueOf(map.get("roleMergeFlag"))));
//            }
//
//            if (map.get("imageUrl") != null) {
//                user.setImageUrl(String.valueOf(map.get("imageUrl")));
//            }
//
//            if (map.containsKey("apiEncryptFlag")) {
//                user.setApiEncryptFlag((Integer) map.get("apiEncryptFlag"));
//            }
//
//            if (isClientOnly) {
//                user.setClientId(Long.parseLong(String.valueOf(map.get("clientId"))));
//                user.setClientName((String) map.get("clientName"));
//                user.setClientAccessTokenValiditySeconds(Integer.parseInt(String.valueOf(map.get("clientAccessTokenValiditySeconds"))));
//                user.setClientRefreshTokenValiditySeconds(Integer.parseInt(String.valueOf(map.get("clientRefreshTokenValiditySeconds"))));
//                user.setClientAuthorizedGrantTypes((Collection) map.get("clientAuthorizedGrantTypes"));
//                user.setClientAutoApproveScopes((Collection) map.get("clientAutoApproveScopes"));
//                user.setClientRegisteredRedirectUri((Collection) map.get("clientRegisteredRedirectUri"));
//                user.setClientResourceIds((Collection) map.get("clientResourceIds"));
//                user.setClientScope((Collection) map.get("clientScope"));
//            } else if (oauth2request != null) {
//                user.setClientName(oauth2request.containsKey("clientId") ? String.valueOf(oauth2request.get("clientId")) : null);
//            }
//
//            try {
//                if (map.get("additionInfo") != null) {
//                    user.setAdditionInfo((Map) map.get("additionInfo"));
//                }
//            } catch (Exception var9) {
//                LOGGER.warn("Parser addition info error:{}", var9.getMessage());
//            }
//
//            try {
//                if (map.get("additionInfoMeaning") != null) {
//                    user.setAdditionInfoMeaning((Map) map.get("additionInfoMeaning"));
//                }
//            } catch (Exception var8) {
//                LOGGER.warn("Parser addition meaning info error:{}", var8.getMessage());
//            }
//
//            return user;
//        } else if (map.containsKey("id")) {
//            CustomUserDetails user = new CustomUserDetails("ANONYMOUS", "unknown password", Collections.emptyList());
////            user.setUserId(BaseConstants.ANONYMOUS_USER_ID);
//            user.setClientId(Long.valueOf(String.valueOf(map.get("id"))));
//            user.setLanguage("zh_CN");
//            user.setTimeZone("GMT+8");
//            if (map.containsKey("scope")) {
//                user.setClientScope(Collections.singleton(String.valueOf(map.get("scope"))));
//            }
//
//            if (map.containsKey("organizationId")) {
//                user.setOrganizationId(Long.valueOf(String.valueOf(map.get("organizationId"))));
//            }
//
//            if (map.containsKey("currentRoleId")) {
//                user.setRoleId(Long.valueOf(String.valueOf(map.get("currentRoleId"))));
//            }
//
//            if (map.containsKey("currentTenantId")) {
//                user.setTenantId(Long.valueOf(String.valueOf(map.get("currentTenantId"))));
//            }
//
//            Object autoApproves;
//            List autoApproveList;
//            if (map.get("roleIds") != null) {
//                autoApproves = map.get("roleIds");
//                if (autoApproves instanceof List) {
//                    autoApproveList = (List) autoApproves;
//                    user.setRoleIds((List) autoApproveList.stream().map((item) -> {
//                        return Long.valueOf(String.valueOf(item));
//                    }).collect(Collectors.toList()));
//                }
//            }
//
//            if (map.get("tenantIds") != null) {
//                autoApproves = map.get("tenantIds");
//                if (autoApproves instanceof List) {
//                    autoApproveList = (List) autoApproves;
//                    user.setTenantIds((List) autoApproveList.stream().map((item) -> {
//                        return Long.valueOf(String.valueOf(item));
//                    }).collect(Collectors.toList()));
//                }
//            }
//
//            if (map.containsKey("client_id")) {
//                user.setClientName(String.valueOf(map.get("client_id")));
//            }
//
//            if (map.get("resource_ids") != null) {
//                autoApproves = map.get("resource_ids");
//                if (autoApproves instanceof List) {
//                    autoApproveList = (List) autoApproves;
//                    user.setClientResourceIds((Collection) autoApproveList.stream().map(String::valueOf).collect(Collectors.toList()));
//                }
//            }
//
//            if (map.get("authorized_grant_types") != null) {
//                autoApproves = map.get("authorized_grant_types");
//                if (autoApproves instanceof List) {
//                    autoApproveList = (List) autoApproves;
//                    user.setClientAuthorizedGrantTypes((Collection) autoApproveList.stream().map(String::valueOf).collect(Collectors.toList()));
//                }
//            }
//
//            if (map.get("redirect_uri") != null) {
//                autoApproves = map.get("redirect_uri");
//                if (autoApproves instanceof List) {
//                    autoApproveList = (List) autoApproves;
//                    user.setClientRegisteredRedirectUri((Collection) autoApproveList.stream().map(String::valueOf).collect(Collectors.toList()));
//                }
//            }
//
//            if (map.get("autoapprove") != null) {
//                autoApproves = map.get("autoapprove");
//                if (autoApproves instanceof List) {
//                    autoApproveList = (List) autoApproves;
//                    user.setClientAutoApproveScopes((Collection) autoApproveList.stream().map(String::valueOf).collect(Collectors.toList()));
//                }
//            }
//
//            if (map.containsKey("access_token_validity")) {
//                user.setClientAccessTokenValiditySeconds(Integer.valueOf(String.valueOf(map.get("access_token_validity"))));
//            }
//
//            if (map.containsKey("refresh_token_validity")) {
//                user.setClientRefreshTokenValiditySeconds(Integer.valueOf(String.valueOf(map.get("refresh_token_validity"))));
//            }
//
//            if (map.containsKey("timeZone")) {
//                user.setTimeZone(String.valueOf(map.get("timeZone")));
//            }
//
//            if (map.containsKey("apiEncryptFlag")) {
//                user.setApiEncryptFlag((Integer) map.get("apiEncryptFlag"));
//            }
//
//            return user;
//        } else {
//            return null;
//        }
//    }
//}
