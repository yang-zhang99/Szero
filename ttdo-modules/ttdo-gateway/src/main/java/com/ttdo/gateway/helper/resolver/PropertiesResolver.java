package com.ttdo.gateway.helper.resolver;

import com.ttdo.gateway.helper.entity.CommonRoute;

import java.util.Map;

/**
 *
 * @param <T>
 */
public interface PropertiesResolver<T> {

    Map<String, CommonRoute> resolveRoutes(T properties);

}
