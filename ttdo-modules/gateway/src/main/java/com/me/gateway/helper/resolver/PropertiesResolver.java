package com.me.gateway.helper.resolver;

import com.me.gateway.helper.domain.entity.CommonRoute;

import java.util.Map;

/**
 *
 * @param <T>
 */
public interface PropertiesResolver<T> {

    Map<String, CommonRoute> resolveRoutes(T properties);

}
