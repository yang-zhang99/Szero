package com.ttdo.autoconfigure.gateway.helper.api;


import com.ttdo.gateway.helper.config.GatewayHelperProperties;
import com.ttdo.gateway.helper.config.GatewayPropertiesWrapper;
import com.ttdo.gateway.helper.resolver.GatewayPropertiesResolver;
import com.ttdo.gateway.helper.resolver.PropertiesResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.jwt.crypto.sign.MacSigner;
import org.springframework.security.jwt.crypto.sign.Signer;


@Configuration
@EnableConfigurationProperties(GatewayHelperProperties.class)
public class GatewayHelperConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(GatewayHelperConfiguration.class);

    /**
     *  todo
     * @param gatewayHelperProperties
     * @return Signer
     */
    @Bean
    @ConditionalOnMissingBean(Signer.class)
    public Signer jwtSigner(GatewayHelperProperties gatewayHelperProperties) {
        return new MacSigner(gatewayHelperProperties.getJwtKey());
    }

    /**
     *
     * @param applicationContext applicationContext
     * @return PropertiesResolver
     */
    @Bean
    public PropertiesResolver propertiesResolver(ApplicationContext applicationContext){
        Object bean = fallback(() ->
                applicationContext.getBean("primaryRouteLocator"));
        if(bean != null){
//            return new ZuulPropertiesResolver(bean);
        }else if((bean = fallback(() ->
                applicationContext.getBean("routeDefinitionLocator"))) != null){
            return new GatewayPropertiesResolver(bean);
        }
        throw new IllegalStateException("Must rely on a kind of zuul or gateway.");
    }

    /**
     *
     * @param applicationContext applicationContext
     * @return GatewayPropertiesWrapper
     */
    @Bean
    public GatewayPropertiesWrapper propertiesWrapper(ApplicationContext applicationContext){
        Object bean = fallback(() ->
                applicationContext.getBean("zuul-org.springframework.cloud.netflix.zuul.filters.ZuulProperties"));
        if(bean != null){
            return new GatewayPropertiesWrapper(bean);
        }else if((bean = fallback(() ->
                applicationContext.getBean("gatewayProperties"))) != null){
            return new GatewayPropertiesWrapper(bean);
        }

        throw new IllegalStateException("Must rely on a kind of zuul or gateway.");
    }

    private Object fallback(Function function) {
        Object returnVal = null;
        try {
            returnVal = function.apply();
        }catch (Throwable e){
            //fallback
            LOGGER.debug("contain getBean failed.");
        }
        return returnVal;
    }

}
@FunctionalInterface
interface Function{
    Object apply();
}