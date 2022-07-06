package com.ttdo.autoconfigure.gateway;

import com.ttdo.gateway.filter.IpCheckedFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConditionAutoConfiguration {


    @Bean
    public IpCheckedFilter ipCheckedFilter() {
        return new IpCheckedFilter();
    }

}
