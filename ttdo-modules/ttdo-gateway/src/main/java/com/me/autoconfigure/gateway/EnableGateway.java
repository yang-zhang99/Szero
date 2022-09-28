package com.me.autoconfigure.gateway;


//import com.me.core.resource.annoation.EnableResourceServer;
import com.me.gateway.config.YGatewayProperties;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({
        WebConditionAutoConfiguration.class,
        YGatewayProperties.class
})


public @interface EnableGateway {

}
