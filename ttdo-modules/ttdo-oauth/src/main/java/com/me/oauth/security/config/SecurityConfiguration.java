package com.me.oauth.security.config;

import com.me.oauth.domain.repository.AuditLoginRepository;
import com.me.oauth.domain.repository.BaseClientRepository;
import com.me.oauth.domain.repository.ClientRepository;
import com.me.oauth.domain.repository.UserRepository;
import com.me.oauth.domain.service.AuditLoginService;
import com.me.oauth.domain.service.impl.AuditLoginServiceImpl;
import com.me.oauth.infra.constant.Constants;
import com.me.oauth.security.custom.*;
import com.me.oauth.security.custom.processor.login.LoginSuccessProcessor;
import com.me.oauth.security.service.*;
import com.me.oauth.security.service.impl.DefaultClientDetailsWrapper;
import com.me.oauth.security.service.impl.DefaultLoginRecordService;
import com.me.oauth.security.service.impl.DefaultUserAccountService;
import com.me.oauth.security.service.impl.DefaultUserDetailsBuilder;
import com.me.oauth.security.util.LoginUtil;
import com.yang.redis.RedisHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.PortMapper;
import org.springframework.security.web.PortMapperImpl;
import org.springframework.security.web.PortResolver;
import org.springframework.security.web.PortResolverImpl;

import java.util.List;
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
    @Autowired
    private AuditLoginRepository auditLoginRepository;
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

    @Bean
    public PortMapper portMapper() {
        PortMapperImpl portMapper = new PortMapperImpl();
        Map<String, String> portMap = securityProperties.getPortMapper().stream()
                .collect(Collectors.toMap(m -> String.valueOf(m.getSourcePort()), m -> String.valueOf(m.getSourcePort())));
        portMapper.setPortMappings(portMap);
        return portMapper;
    }

    @Bean
    public PortResolver portResolver() {
        PortResolverImpl portResolver = new PortResolverImpl();
        portResolver.setPortMapper(portMapper());
        return portResolver;
    }

    @Bean
    @ConditionalOnMissingBean(AuditLoginService.class)
    public AuditLoginService auditLoginService() {
        return new AuditLoginServiceImpl(auditLoginRepository, userRepository, tokenStore());
    }

    @Bean
    @ConditionalOnMissingBean(CustomAuthenticationFailureHandler.class)
    @DependsOn("loginRecordService")
    public CustomAuthenticationFailureHandler authenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler(loginRecordService(), securityProperties, auditLoginService());
    }


    @Bean
    @ConditionalOnMissingBean(CustomAuthenticationSuccessHandler.class)
    public CustomAuthenticationSuccessHandler authenticationSuccessHandler(List<LoginSuccessProcessor> successProcessors) {
        return new CustomAuthenticationSuccessHandler(securityProperties, successProcessors);
    }

    @Bean
    @ConditionalOnMissingBean(ClientDetailsWrapper.class)
    public ClientDetailsWrapper clientDetailsWrapper(ClientRepository clientRepository) {
        return new DefaultClientDetailsWrapper(clientRepository);
    }


}