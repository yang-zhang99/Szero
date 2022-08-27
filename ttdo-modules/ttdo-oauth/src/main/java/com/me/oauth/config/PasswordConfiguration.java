package com.me.oauth.config;



import com.me.oauth.domain.service.BaseUserService;
import com.me.oauth.domain.service.PasswordErrorTimesService;
import com.me.oauth.domain.service.UserPasswordService;
import com.me.oauth.domain.service.impl.BaseUserServiceImpl;
import com.me.oauth.domain.service.impl.PasswordErrorTimesServiceImpl;
import com.me.oauth.domain.service.impl.UserPasswordServiceImpl;
import com.me.oauth.security.custom.CustomBCryptPasswordEncoder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableConfigurationProperties({PasswordProperties.class})
public class PasswordConfiguration {
    public PasswordConfiguration() {
    }

    @Bean
    @ConditionalOnMissingBean({BaseUserService.class})
    public BaseUserService baseUserService() {
        return new BaseUserServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean({PasswordErrorTimesService.class})
    public PasswordErrorTimesService passwordErrorTimesService() {
        return new PasswordErrorTimesServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean({UserPasswordService.class})
    public UserPasswordService userPasswordService() {
        return new UserPasswordServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean({PasswordEncoder.class})
    public PasswordEncoder passwordEncoder() {
        return new CustomBCryptPasswordEncoder();
    }
}
