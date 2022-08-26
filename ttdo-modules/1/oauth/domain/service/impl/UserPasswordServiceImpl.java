package com.me.oauth.domain.service.impl;


import com.me.oauth.domain.service.UserPasswordService;
import org.springframework.stereotype.Service;

@Service
public class UserPasswordServiceImpl implements UserPasswordService {
    @Override
    public void updateUserPassword(Long userId, String newPassword) {

    }

    @Override
    public void resetUserPassword(Long userId, Long tenantId, boolean ldapUpdatable) {

    }

    @Override
    public void updateUserPassword(Long userId, String newPassword, boolean ldapUpdatable) {

    }

    @Override
    public String getTenantDefaultPassword(Long tenantId) {
        return null;
    }
//    @Autowired
//    private BaseUserRepository baseUserRepository;
//    @Autowired
//    private BasePasswordPolicyRepository basePasswordPolicyRepository;
//    @Autowired
//    private BaseUserInfoRepository baseUserInfoRepository;
//    @Autowired
//    private BasePasswordHistoryRepository basePasswordHistoryRepository;
//    @Autowired
//    private com.ttdo.oauth.policy.PasswordPolicyManager passwordPolicyManager;
//    @Autowired
//    private PasswordErrorTimesService passwordErrorTimesService;
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//    private static final String DEFAULT_PASSWORD = "123456";
//
//    public UserPasswordServiceImpl() {
//    }
//
//    public void updateUserPassword(Long userId, String newPassword) {
//        this.updateUserPassword(userId, newPassword, false);
//    }
//
//    public void resetUserPassword(Long userId, Long tenantId, boolean ldapUpdatable) {
//        this.updateUserPassword(userId, this.getTenantDefaultPassword(tenantId), ldapUpdatable);
//    }
//
//    public void updateUserPassword(Long userId, String newPassword, boolean ldapUpdatable) {
//        BaseUser user = (BaseUser)this.baseUserRepository.selectByPrimaryKey(userId);
//        BaseUserInfo userInfo = (BaseUserInfo)this.baseUserInfoRepository.selectByPrimaryKey(userId);
//        if (user != null && userInfo != null) {
//            if (!ldapUpdatable) {
//                this.checkLdapUser(user);
//            }
//
//            this.checkPasswordSame(user, newPassword);
//            this.checkPasswordPolicy(user, newPassword);
//            this.recordHistoryPassword(user);
//            this.updatePassword(user, newPassword);
//            this.updateUserInfo(user, userInfo, newPassword);
//            this.afterHandle(user);
//        } else {
//            throw new CommonException("hoth.warn.password.userNotFound", new Object[0]);
//        }
//    }
//
//    public String getTenantDefaultPassword(Long tenantId) {
//        BasePasswordPolicy passwordPolicy = this.basePasswordPolicyRepository.selectPasswordPolicy(tenantId);
//        return (String)StringUtils.defaultIfBlank(passwordPolicy.getOriginalPassword(), "123456");
//    }
//
//    protected void checkLdapUser(BaseUser user) {
//        if (user.getLdap() != null && user.getLdap()) {
//            throw new CommonException("hoth.warn.password.ldapUserCantUpdatePassword", new Object[0]);
//        }
//    }
//
//    protected void checkPasswordSame(BaseUser user, String newPassword) {
//        if (this.passwordEncoder.matches(newPassword, user.getPassword())) {
//            throw new CommonException("hoth.warn.password.same", new Object[0]);
//        }
//    }
//
//    protected void checkPasswordPolicy(BaseUser user, String newPassword) {
//        this.passwordPolicyManager.passwordValidate(newPassword, user.getOrganizationId(), user);
//    }
//
//    protected void recordHistoryPassword(BaseUser user) {
//        BasePasswordHistory passwordHistory = (new BasePasswordHistory(user.getId(), user.getPassword())).setTenantId(user.getOrganizationId());
//        this.basePasswordHistoryRepository.insertSelective(passwordHistory);
//    }
//
//    protected void updatePassword(BaseUser user, String newPassword) {
//        user.setPassword(this.passwordEncoder.encode(newPassword));
//        user.setLastPasswordUpdatedAt(new Date());
//        user.setLocked(false);
//        user.setLockedUntilAt((Date)null);
//        this.baseUserRepository.updateOptional(user, new String[]{"password", "lastPasswordUpdatedAt", "isLocked", "lockedUntilAt"});
//    }
//
//    protected void updateUserInfo(BaseUser user, BaseUserInfo baseUserInfo, String newPassword) {
//        baseUserInfo.setPasswordResetFlag(BaseConstants.Flag.YES);
//        baseUserInfo.setSecurityLevelCode(CheckStrength.getPasswordLevel(newPassword).name());
//        this.baseUserInfoRepository.updateOptional(baseUserInfo, new String[]{"passwordResetFlag", "securityLevelCode"});
//    }
//
//    protected void afterHandle(BaseUser user) {
//        user.setPassword((String)null);
//        this.passwordErrorTimesService.clearErrorTimes(user.getId());
//    }
}
