//package com.ttdo.gateway.helper.filter;
//
//import com.ttdo.core.oauth.CustomUserDetails;
//import com.ttdo.gateway.helper.api.HelperFilter;
//import com.ttdo.gateway.helper.entity.CheckState;
//import com.ttdo.gateway.helper.entity.PermissionDO;
//import com.ttdo.gateway.helper.entity.RequestContext;
//import com.ttdo.gateway.helper.infra.mapper.PermissionPlusMapper;
//import com.ttdo.gateway.helper.service.CustomPermissionCheckService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
//import java.util.Collections;
//import java.util.List;
//
///**
// * 管理角色权限
// */
//@Component
//public class AdminRolePermissionFilter implements HelperFilter {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(AdminRolePermissionFilter.class);
//    private final PermissionPlusMapper permissionPlusMapper;
//    private Long siteSuperAdminRoleId = -1L;
//    private Long tenantSuperAdminRoleId = -1L;
//    private List<String> parameterTenantId;
//    private boolean checkTenant;
//    private CustomPermissionCheckService customPermissionCheckService;
//
//    public AdminRolePermissionFilter(PermissionPlusMapper permissionPlusMapper,
//                                     List<String> parameterTenantId,
//                                     CustomPermissionCheckService customPermissionCheckService) {
//        this.permissionPlusMapper = permissionPlusMapper;
//        this.parameterTenantId = parameterTenantId;
//        this.customPermissionCheckService = customPermissionCheckService;
//    }
//
//    @Override
//    public int filterOrder() {
//        return 80;
//    }
//
//    @Override
//    public boolean shouldFilter(RequestContext requestContext) {
//        return true;
//    }
//
//    @Override
//    public boolean run(RequestContext context) {
//        // 权限
//        PermissionDO permissionDO = context.getPermissionDO();
//        // 用户信息
//        CustomUserDetails details = context.getCustomUserDetails();
//
//        Long memberId = null;
//        List<Long> roleIds = Collections.emptyList();
//        String memberType = null;
//
//        if (details.getClientId() != null) {
//            memberId = details.getClientId();
//            roleIds = details.getRoleIds();
//            memberType = "client";
//        } else if (details.getUserId() != null) {
//            memberId = details.getUserId();
//            roleIds = details.roleMergeIds();
//            memberType = "user";
//        }
//
//        boolean isSiteSuperRole = roleIds.contains(this.siteSuperAdminRoleId);
//        boolean isTenantSuperRole = roleIds.contains(this.tenantSuperAdminRoleId);
//        if (!isSiteSuperRole && !isTenantSuperRole) {
//            return true;
//        } else {
//            int availableCount = 0;
//            if (isSiteSuperRole) {
//                availableCount = this.permissionPlusMapper.countAvailableRole(memberId, memberType, this.siteSuperAdminRoleId);
//            } else {
//                availableCount = this.permissionPlusMapper.countAvailableRole(memberId, memberType, this.tenantSuperAdminRoleId);
//            }
//
//            LOGGER.debug("Admin role check: memberId={}, memberType={}, roleIds={}, availableRoleCount={}", new Object[]{memberId, memberType, roleIds, availableCount});
//            if (availableCount < 1) {
//                if (roleIds.size() > 1) {
//                    return true;
//                } else {
//                    context.response.setStatus(CheckState.MEMBER_ROLE_EXPIRED);
//                    context.response.setMessage("MemberRole expired, roleId: " + roleIds);
//                    return false;
//                }
//            } else {
////                boolean lov = StringUtils.isNotEmpty(context.getLovCode());
////                if (lov) {
////                    context.response.setStatus(CheckState.SUCCESS_PASS_SITE);
////                    context.response.setMessage("Have access to this lov : " + context.getLovCode());
////                    return false;
////                } else {
////                    if (isSiteSuperRole) {
////                        if (!ResourceLevel.SITE.value().equals(permission.getFdLevel())) {
////                            context.response.setStatus(CheckState.PERMISSION_LEVEL_MISMATCH);
////                            context.response.setMessage("Site role no access to this 'non-site-level' interface, permission: " + context.getPermission());
////                        } else {
////                            context.response.setStatus(CheckState.SUCCESS_PASS_SITE);
////                            context.response.setMessage("Have access to this 'site-level' interface, permission: " + context.getPermission());
////                        }
////                    } else if (!StringUtils.equalsAny(permission.getFdLevel(), new CharSequence[]{ResourceLevel.ORGANIZATION.value(), ResourceLevel.PROJECT.value()})) {
////                        context.response.setStatus(CheckState.PERMISSION_LEVEL_MISMATCH);
////                        context.response.setMessage("Tenant role no access to this 'non-tenant-level' interface, permission: " + context.getPermission());
////                    } else if (ResourceLevel.ORGANIZATION.value().equals(permission.getFdLevel())) {
////                        this.checkTenantPermission(context, permission.getPath());
////                    } else {
////                        this.checkCustomLevelPermission(context, permission.getPath());
////                    }
////
////                    return false;
////                }
//            }
//        }
//
//        return false;
//    }
//
////
////    private void checkTenantPermission(final RequestContext context, final String matchPath) {
////        Long tenantId = UrlUtils.parseLongValueFromUri(context.getTrueUri(), matchPath, this.parameterTenantId);
////        if (this.checkTenant) {
////            if (tenantId == null) {
////                context.response.setStatus(CheckState.API_ERROR_ORG_ID);
////                context.response.setMessage("Organization interface must have 'organizationId' or 'organization_id' in path");
////            } else {
////                boolean accessOrg = context.getCustomUserDetails().getTenantIds().contains(tenantId);
////                if (accessOrg) {
////                    context.response.setStatus(CheckState.SUCCESS_PASS_ORG);
////                    context.response.setMessage("Have access to this 'organization-level' interface, permission: " + context.getPermission());
////                } else {
////                    context.response.setStatus(CheckState.PERMISSION_NOT_PASS_ORG);
////                    context.response.setMessage("No access to this this organization, organizationId: " + tenantId);
////                }
////            }
////        } else {
////            context.response.setStatus(CheckState.SUCCESS_PASS_ORG);
////            context.response.setMessage("Do not check organizationId, have access to this 'organization-level' interface, permission: " + context.getPermission());
////        }
////
////    }
////
////    private void checkCustomLevelPermission(final RequestContext context, final String matchPath) {
////        if (this.customPermissionCheckService != null) {
////            this.customPermissionCheckService.checkPermission(context);
////        }
////
////    }
////
////    public void afterPropertiesSet() throws Exception {
////        List<RoleVO> superRoles = this.permissionPlusMapper.selectSuperAdminRole();
////        RoleVO tenantSuperRole = (RoleVO)superRoles.stream().filter((r) -> {
////            return "role/organization/default/administrator".equals(r.getCode());
////        }).findFirst().orElse((Object)null);
////        RoleVO siteSuperRole = (RoleVO)superRoles.stream().filter((r) -> {
////            return "role/site/default/administrator".equals(r.getCode());
////        }).findFirst().orElse((Object)null);
////        if (tenantSuperRole != null) {
////            this.tenantSuperAdminRoleId = tenantSuperRole.getId();
////            if (siteSuperRole != null) {
////                this.siteSuperAdminRoleId = siteSuperRole.getId();
////            } else {
////                throw new IllegalStateException("Site super admin role not found. roleCode is role/site/default/administrator");
////            }
////        } else {
////            throw new IllegalStateException("Tenant super admin role not found. roleCode is role/organization/default/administrator");
////        }
////    }
//}
