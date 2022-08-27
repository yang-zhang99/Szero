package com.me.oauth.security.config;

import com.me.oauth.domain.repository.*;
import com.me.oauth.domain.service.AuditLoginService;
import com.me.oauth.domain.service.BaseUserService;
import com.me.oauth.domain.service.PasswordErrorTimesService;
import com.me.oauth.domain.service.impl.AuditLoginServiceImpl;
import com.me.oauth.infra.constant.Constants;
import com.me.oauth.infra.encrypt.EncryptClient;
import com.me.oauth.policy.PasswordPolicyManager;
import com.me.oauth.security.custom.*;
import com.me.oauth.security.custom.processor.login.LoginSuccessProcessor;
import com.me.oauth.security.resource.ResourceMatcher;
import com.me.oauth.security.resource.impl.MobileResourceMatcher;
import com.me.oauth.security.service.*;
import com.me.oauth.security.service.impl.*;
import com.me.oauth.security.util.LoginUtil;
import com.me.redis.RedisHelper;
import com.me.redis.captcha.CaptchaImageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
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
import org.springframework.session.SessionRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@EnableConfigurationProperties({SecurityProperties.class})
public class SecurityConfiguration {

    @Autowired
    private CaptchaImageHelper captchaImageHelper;
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

//    @Autowired(required = false)
//    private DomainRepository domainRepository;
//    @Autowired
//    private SsoProperties ssoProperties;
    @Autowired
    private BaseUserService baseUserService;
    @Autowired
    private BaseLdapRepository baseLdapRepository;
    @Autowired
    private BasePasswordPolicyRepository basePasswordPolicyRepository;
    @Autowired
    private BaseClientRepository baseClientRepository;
    @Autowired
    private PasswordErrorTimesService passwordErrorTimesService;
    @Autowired
    private PasswordPolicyManager passwordPolicyManager;

    @Autowired
    private SessionRepository<?> sessionRepository;
    @Autowired
    private AuditLoginRepository auditLoginRepository;

    @Bean
    @ConditionalOnMissingBean(ResourceMatcher.class)
    @ConditionalOnProperty(prefix = SecurityProperties.PREFIX, name = "custom-resource-matcher", havingValue = "true")
    public ResourceMatcher resourceMatcher () {
        return new MobileResourceMatcher();
    }

    /**
     * 用户账户业务服务
     */
    @Bean
    @ConditionalOnMissingBean(UserAccountService.class)
    public UserAccountService userAccountService() {
        return new DefaultUserAccountService(
                this.userRepository,
                this.baseUserService,
                this.passwordPolicyManager,
                this.basePasswordPolicyRepository,
                this.baseClientRepository,
                this.securityProperties);
    }

    /**
     * 登录记录业务服务
     */
    @Bean
    @ConditionalOnMissingBean(LoginRecordService.class)
    public LoginRecordService loginRecordService() {
        return new DefaultLoginRecordService(baseUserService, passwordErrorTimesService, basePasswordPolicyRepository, redisHelper);
    }

    @Bean
    @ConditionalOnMissingBean(UserDetailsWrapper.class)
    public UserDetailsWrapper userDetailsWrapper(RedisHelper redisHelper) {
        return new DefaultUserDetailsWrapper(userRepository, redisHelper);
    }

    @Bean
    @ConditionalOnMissingBean(ClientDetailsWrapper.class)
    public ClientDetailsWrapper clientDetailsWrapper(ClientRepository clientRepository) {
        return new DefaultClientDetailsWrapper(clientRepository);
    }

    @Bean
    @ConditionalOnMissingBean(UserDetailsBuilder.class)
    public UserDetailsBuilder userDetailsBuilder(UserDetailsWrapper userDetailsWrapper) {
        return new DefaultUserDetailsBuilder(userDetailsWrapper,
//                domainRepository, ssoProperties,
                userAccountService());
    }

    /**
     * Sso用户账户业务服务
     */
//    @Bean
//    @ConditionalOnMissingBean(SsoUserAccountService.class)
//    public SsoUserAccountService ssoUserAccountService() {
//        return new DefaultSsoUserAccountService(userRepository, securityProperties);
//    }
//
//    @Bean
//    @ConditionalOnMissingBean(SsoUserDetailsBuilder.class)
//    public SsoUserDetailsBuilder ssoUserDetailsBuilder(UserDetailsWrapper userDetailsWrapper) {
//        return new DefaultSsoUserDetailsBuilder(userDetailsWrapper, domainRepository, ssoProperties, userAccountService());
//    }

    @Bean
    @ConditionalOnMissingBean(CustomAuthenticationDetailsSource.class)
    public CustomAuthenticationDetailsSource authenticationDetailsSource () {
        return new CustomAuthenticationDetailsSource(captchaImageHelper);
    }

    @Bean
    @ConditionalOnMissingBean(CustomAuthenticationSuccessHandler.class)
    public CustomAuthenticationSuccessHandler authenticationSuccessHandler (List<LoginSuccessProcessor> successProcessors) {
        return new CustomAuthenticationSuccessHandler(securityProperties, successProcessors);
    }

    @Bean
    @ConditionalOnMissingBean(AuditLoginService.class)
    public AuditLoginService auditLoginService () {
        return new AuditLoginServiceImpl(auditLoginRepository, userRepository, tokenStore());
    }

    @Bean
    @ConditionalOnMissingBean(CustomAuthenticationFailureHandler.class)
    public CustomAuthenticationFailureHandler authenticationFailureHandler () {
        return new CustomAuthenticationFailureHandler(loginRecordService(), securityProperties, auditLoginService());
    }

//    @Bean
//    @ConditionalOnMissingBean(CustomLogoutSuccessHandler.class)
//    public CustomLogoutSuccessHandler logoutSuccessHandler (List<LogoutSuccessProcessor> postProcessors) {
//        return new CustomLogoutSuccessHandler(tokenStore(), loginRecordService(), securityProperties, ssoProperties,domainRepository,
//                userAccountService() , postProcessors);
//    }

    @Bean
    @ConditionalOnMissingBean(CustomUserDetailsService.class)
    public CustomUserDetailsService userDetailsService (UserAccountService userAccountService,
                                                        UserDetailsBuilder userDetailsBuilder,
                                                        LoginRecordService loginRecordService) {
        return new CustomUserDetailsService(userAccountService, userDetailsBuilder, loginRecordService);
    }

    //@Bean
    //@ConditionalOnMissingBean(CustomClientDetailsService.class)
    //public CustomClientDetailsService clientDetailsService (BaseClientRepository baseClientRepository, ClientDetailsWrapper clientDetailsWrapper) {
    //    return new CustomClientDetailsService(baseClientRepository, clientDetailsWrapper);
    //}

//    @Bean
//    @ConditionalOnMissingBean(CustomAuthenticationProvider.class)
//    public CustomAuthenticationProvider authenticationProvider (CustomUserDetailsService userDetailsService,
//                                                                EncryptClient encryptClient,
//                                                                PasswordEncoder passwordEncoder) {
//        CustomAuthenticationProvider provider = new CustomAuthenticationProvider(
//                userDetailsService, baseLdapRepository,
//                userAccountService(), loginRecordService(),
//                captchaImageHelper, securityProperties,
//                encryptClient, userRepository);
//
//        provider.setPasswordEncoder(passwordEncoder);
//        return provider;
//    }

    @Bean
    @ConditionalOnMissingBean(CustomAuthenticationKeyGenerator.class)
    public CustomAuthenticationKeyGenerator authenticationKeyGenerator () {
        return new CustomAuthenticationKeyGenerator(loginUtil);
    }

    @Bean
    @ConditionalOnMissingBean(CustomRedisTokenStore.class)
    public CustomRedisTokenStore tokenStore() {
        CustomRedisTokenStore redisTokenStore = new CustomRedisTokenStore(redisConnectionFactory, loginUtil, sessionRepository);
        redisTokenStore.setAuthenticationKeyGenerator(authenticationKeyGenerator());
        redisTokenStore.setPrefix(Constants.CacheKey.ACCESS_TOKEN);
        return redisTokenStore;
    }

    //
    // social config
    // ------------------------------------------------------------------------------
//
//    @Bean
//    @ConditionalOnMissingBean(SocialProviderRepository.class)
//    public SocialProviderRepository socialProviderRepository() {
//        return new CustomSocialProviderRepository();
//    }
//
//    @Bean
//    @ConditionalOnMissingBean(SocialUserProviderRepository.class)
//    public SocialUserProviderRepository socialUserProviderRepository() {
//        return new CustomSocialUserProviderRepository();
//    }
//
//    @Bean
//    @ConditionalOnMissingBean(SocialUserDetailsService.class)
//    public SocialUserDetailsService socialUserDetailsService(UserAccountService userAccountService,
//                                                             UserDetailsBuilder userDetailsBuilder,
//                                                             LoginRecordService loginRecordService) {
//        return new CustomSocialUserDetailsService(userAccountService, userDetailsBuilder, loginRecordService);
//    }
//
//    @Bean
//    @ConditionalOnMissingBean(SocialAuthenticationProvider.class)
//    public SocialAuthenticationProvider socialAuthenticationProvider(SocialUserProviderRepository socialUserProviderRepository,
//                                                                     SocialUserDetailsService socialUserDetailsService) {
//        return new CustomSocialAuthenticationProvider(socialUserProviderRepository, socialUserDetailsService);
//    }
//
//    @Bean
//    @ConditionalOnMissingBean(SocialSuccessHandler.class)
//    public SocialSuccessHandler socialSuccessHandler(SecurityProperties securityProperties,
//                                                     List<LoginSuccessProcessor> successProcessors) {
//        return new CustomSocialSuccessHandler(securityProperties, successProcessors);
//    }
//
//    @Bean
//    @ConditionalOnMissingBean(CustomSocialFailureHandler.class)
//    public CustomSocialFailureHandler socialFailureHandler(SecurityProperties securityProperties) {
//        return new CustomSocialFailureHandler(securityProperties);
//    }


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
}