package com.ttdo.gateway.helper.filter;

import com.ttdo.gateway.helper.api.HelperFilter;
import com.ttdo.gateway.helper.entity.CheckState;
import com.ttdo.gateway.helper.entity.PermissionDO;
import com.ttdo.gateway.helper.entity.RequestContext;
import com.ttdo.gateway.helper.service.PermissionService;
import com.ttdo.gateway.helper.util.ServerRequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class GetPermissionFilter implements HelperFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(GetPermissionFilter.class);

    private final PermissionService permissionService;
//    private final IEncryptionService encryptionService;

    public GetPermissionFilter(PermissionService permissionService
//                               IEncryptionService encryptionService
    ) {
        this.permissionService = permissionService;
//        this.encryptionService = encryptionService;
    }

    @Override
    public int filterOrder() {
        return 20;
    }

    @Override
    public boolean shouldFilter(RequestContext context) {
        return true;
    }

    @Override
    public boolean run(RequestContext context) {
        // 设置请求的菜单ID
        context.setMenuId(getMenuId(context));

        String key = context.getRequestKey();
        PermissionDO permission = permissionService.selectPermissionByRequest(key);
        if (permission == null) {
            context.response.setStatus(CheckState.PERMISSION_MISMATCH);
            context.response.setMessage("This request mismatch any permission");
            return false;
        } else if (permission.getWithin()) {
            context.response.setStatus(CheckState.PERMISSION_WITH_IN);
            context.response.setMessage("No access to within interface");
            return false;
        } else {
            context.setPermission(permission);
        }
        return true;
    }

    private Long getMenuId(RequestContext context) {
        Object servletRequest = context.getServletRequest();
//        String menuId =
//                ServerRequestUtils.getHeaderValue(servletRequest, BaseHeaders.H_MENU_ID);
//
//        if (StringUtils.isEmpty(menuId)) {
//            return null;
//        }
//
        Long id = null;
//        try {
//            if (encryptionService.isCipher(menuId)) {
//                menuId = encryptionService.decrypt(menuId, "", context.request.accessToken);
//            }
//            id = Long.parseLong(menuId);
//        } catch (NumberFormatException e) {
//            LOGGER.warn("Header of [{}] format error, header value is [{}]", BaseHeaders.H_MENU_ID, menuId);
//        }
        return id;
    }
}
