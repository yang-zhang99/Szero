package com.ttdo.oauth.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.PortMapper;
import org.springframework.security.web.PortMapperImpl;

import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@EnableConfigurationProperties({SecurityProperties.class})
public class SecurityConfiguration {

//    @Autowired
//    private SecurityProperties securityProperties;
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private BaseUserService baseUserService;

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


}