package com.ttdo.oauth.security.config;

import com.ttdo.oauth.domain.repository.AuditLoginRepository;
import com.ttdo.oauth.domain.repository.BaseClientRepository;
import com.ttdo.oauth.domain.repository.UserRepository;
import com.ttdo.oauth.infra.constant.Constants;
import com.ttdo.oauth.security.custom.CustomAuthenticationKeyGenerator;
import com.ttdo.oauth.security.custom.CustomBCryptPasswordEncoder;
import com.ttdo.oauth.security.custom.CustomRedisTokenStore;
import com.ttdo.oauth.security.custom.CustomUserDetailsService;
import com.ttdo.oauth.security.service.LoginRecordService;
import com.ttdo.oauth.security.service.UserAccountService;
import com.ttdo.oauth.security.service.UserDetailsBuilder;
import com.ttdo.oauth.security.service.UserDetailsWrapper;
import com.ttdo.oauth.security.service.impl.DefaultLoginRecordService;
import com.ttdo.oauth.security.service.impl.DefaultUserAccountService;
import com.ttdo.oauth.security.service.impl.DefaultUserDetailsBuilder;
import com.ttdo.oauth.security.util.LoginUtil;
import com.yang.redis.RedisHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.PortMapper;
import org.springframework.security.web.PortMapperImpl;
//import org.springframework.session.SessionRepository;

import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@EnableConfigurationProperties({SecurityProperties.class})
public class SecurityConfiguration {

    //    @Autowired
//    private CaptchaImageHelper captchaImageHelper;
    @Autowired
    private RedisHelper redisHelper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SecurityProperties securityProperties;
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;
    @Autowired
    private LoginUtil loginUtil;
//    @Autowired
//    private SessionRepository<?> sessionRepository;
    //    @Autowired(required = false)
//    private DomainRepository domainRepository;
//    @Autowired
//    private SsoProperties ssoProperties;
//    @Autowired
//    private BaseUserService baseUserService;
//    @Autowired
//    private BaseLdapRepository baseLdapRepository;
//    @Autowired
//    private BasePasswordPolicyRepository basePasswordPolicyRepository;
    @Autowired
    private BaseClientRepository baseClientRepository;
    //    @Autowired
//    private PasswordErrorTimesService passwordErrorTimesService;
//    @Autowired
//    private PasswordPolicyManager passwordPolicyManager;
//
//    @Autowired
//    private SessionRepository<?> sessionRepository;
//    @Autowired
//    private AuditLoginRepository auditLoginRepository;

    /**
     * 用户账户业务服务
     */
//    @Bean
//    @ConditionalOnMissingBean(UserAccountService.class)
//    public UserAccountService userAccountService() {
//        return new DefaultUserAccountService(this.userRepository, this.baseUserService, this.passwordPolicyManager,
//                this.basePasswordPolicyRepository, this.baseClientRepository, this.securityProperties);
//    }

//    @Bean
//    public PortMapper portMapper() {
//        PortMapperImpl portMapper = new PortMapperImpl();
//
//        Map<String, String> portMap = securityProperties.getPortMapper().stream()
//                .collect(Collectors.toMap(m -> String.valueOf(m.getSourcePort()), m -> String.valueOf(m.getSourcePort())));
//        portMapper.setPortMappings(portMap);
//        return portMapper;
//    }

    /**
     * 用户账户业务服务
     */
    @Bean
    @ConditionalOnMissingBean(UserAccountService.class)
    public UserAccountService userAccountService() {
        return new DefaultUserAccountService(this.userRepository,
//                this.baseUserService,
//                this.passwordPolicyManager,
//                this.basePasswordPolicyRepository,
                this.baseClientRepository,
                this.securityProperties);
    }

    @Bean
    @ConditionalOnMissingBean(UserDetailsBuilder.class)
    public UserDetailsBuilder userDetailsBuilder(UserDetailsWrapper userDetailsWrapper) {
        return new DefaultUserDetailsBuilder(userDetailsWrapper,
//                domainRepository, ssoProperties,
                userAccountService());
    }

    /**
     * 登录记录业务服务
     */
    @Bean
    @ConditionalOnMissingBean(LoginRecordService.class)
    public LoginRecordService loginRecordService() {
        return new DefaultLoginRecordService(
//                baseUserService, passwordErrorTimesService, basePasswordPolicyRepository,
                redisHelper);
    }

    @Bean
    @ConditionalOnMissingBean(CustomUserDetailsService.class)
    public CustomUserDetailsService userDetailsService(UserAccountService userAccountService,
                                                       UserDetailsBuilder userDetailsBuilder,
                                                       LoginRecordService loginRecordService) {
        return new CustomUserDetailsService(userAccountService, loginRecordService);
    }

    @Bean
    @ConditionalOnMissingBean(PasswordEncoder.class)
    public PasswordEncoder passwordEncoder() {
        return new CustomBCryptPasswordEncoder();
    }

    @Bean
    @ConditionalOnMissingBean(CustomRedisTokenStore.class)
    public CustomRedisTokenStore tokenStore() {
        // todo
        CustomRedisTokenStore redisTokenStore = new CustomRedisTokenStore(redisConnectionFactory, loginUtil);
        redisTokenStore.setAuthenticationKeyGenerator(authenticationKeyGenerator());
        redisTokenStore.setPrefix(Constants.CacheKey.ACCESS_TOKEN);
        return redisTokenStore;
    }

    @Bean
    @ConditionalOnMissingBean(CustomAuthenticationKeyGenerator.class)
    public CustomAuthenticationKeyGenerator authenticationKeyGenerator () {
        return new CustomAuthenticationKeyGenerator(loginUtil);
    }

}