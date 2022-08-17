package com.me.gateway.helper.impl;

import com.me.gateway.helper.api.HelperFilter;
import com.me.gateway.helper.domain.entity.*;
import com.me.gateway.helper.service.PermissionCheckService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.*;
import java.util.stream.Collectors;

public final class HelperChain {

    private static final Logger LOGGER = LoggerFactory.getLogger(HelperChain.class);

    private List<HelperFilter> helperFilters;

    @Autowired
    private PermissionCheckService permissionCheckService;

    private static final Set<CheckState> UNAUTHORIZED = new HashSet<>(Arrays.asList(
            CheckState.PERMISSION_ACCESS_TOKEN_NULL,
            CheckState.PERMISSION_ACCESS_TOKEN_INVALID,
            CheckState.PERMISSION_ACCESS_TOKEN_EXPIRED,
            CheckState.PERMISSION_GET_USE_DETAIL_FAILED
    ));

    public HelperChain(Optional<List<HelperFilter>> optionalHelperFilters) {
        helperFilters = optionalHelperFilters.orElseGet(Collections::emptyList)
                .stream()
                .sorted(Comparator.comparing(HelperFilter::filterOrder))
                .collect(Collectors.toList());
    }

    public ResponseContext doFilter(RequestContext requestContext) {
        CheckResponse checkResponse = requestContext.response;
        ResponseContext responseContext = new ResponseContext();
        try {
            for (HelperFilter t : helperFilters) {
                if (t.shouldFilter(requestContext) && !t.run(requestContext)) {
                    break;
                }
            }
        } catch (Exception e) {
            checkResponse.setStatus(CheckState.EXCEPTION_GATEWAY_HELPER);
            checkResponse.setMessage("gateway helper error happened: " + e.toString());
            LOGGER.info("Check permission error", e);
        }
        if (checkResponse.getStatus().getValue() < HttpStatus.MULTIPLE_CHOICES.value()) {
            responseContext.setHttpStatus(HttpStatus.OK);
            LOGGER.debug("Request 200, context: {}", requestContext);
        } else if (UNAUTHORIZED.contains(checkResponse.getStatus())) {
            responseContext.setHttpStatus(HttpStatus.UNAUTHORIZED);
            LOGGER.info("Request 401, context: {}", requestContext);
        } else if (checkResponse.getStatus().getValue() < HttpStatus.INTERNAL_SERVER_ERROR.value()) {
            responseContext.setHttpStatus(HttpStatus.FORBIDDEN);
            LOGGER.info("Request 403, context: {}", requestContext);
        } else {
            responseContext.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            LOGGER.info("Request 500, context: {}", requestContext);
        }

        if (checkResponse.getMessage() != null) {
            responseContext.setRequestMessage(checkResponse.getMessage());
        }
        responseContext.setJwtToken(checkResponse.getJwt());
        responseContext.setRequestStatus(checkResponse.getStatus().name());
        responseContext.setRequestCode(checkResponse.getStatus().getCode());

        if (PermissionCheck.PERMISSION_CHECK_STATE.contains(checkResponse.getStatus().name())) {
            permissionCheckService.savePermissionCheck(requestContext, checkResponse);
        }
        return responseContext;
    }

}