package com.ttdo.oauth.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yang.core.mybatis.AuditDomain;

//import javax.validation.constraints.NotBlank;
//import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 登录日志审计
 */
@TableName(value = "hpfm_audit_login")
public class AuditLogin extends AuditDomain {

    public static final String FIELD_AUDIT_ID = "auditId";
    public static final String FIELD_AUDIT_TYPE = "auditType";
    public static final String FIELD_USER_ID = "userId";
    public static final String FIELD_LOGIN_NAME = "loginName";
    public static final String FIELD_PHONE = "phone";
    public static final String FIELD_EMAIL = "email";
    public static final String FIELD_TENANT_ID = "tenantId";
    public static final String FIELD_TENANT_NAME = "tenantName";
    public static final String FIELD_LOGIN_DATE = "loginDate";
    public static final String FIELD_LOGIN_IP = "loginIp";
    public static final String FIELD_LOGIN_CLIENT = "loginClient";
    public static final String FIELD_LOGIN_PLATFORM = "loginPlatform";
    public static final String FIELD_LOGIN_OS = "loginOs";
    public static final String FIELD_LOGIN_BROWSER = "loginBrowser";
    public static final String FIELD_LOGIN_STATUS = "loginStatus";
    public static final String FIELD_LOGIN_MESSAGE = "loginMessage";

    //
    // 业务方法(按public protected private顺序排列)
    // ------------------------------------------------------------------------------

    //
    // 数据库字段
    // ------------------------------------------------------------------------------


    @TableId(type = IdType.AUTO)
    private Long auditId;
    @TableField
//    @NotBlank
    private String auditType;
    @TableField
//    @NotNull
    private Long userId;
    @TableField
//    @NotBlank
    private String loginName;
    @TableField
    private String phone;
    @TableField
    private String email;
    @TableField
//    @NotNull
    private Long tenantId;
    @TableField
//    @NotNull
    private Date loginDate;
    @TableField
    private String loginIp;
    @TableField
    private String loginClient;
    @TableField
    private String loginPlatform;
    @TableField
    private String loginOs;
    @TableField
    private String loginBrowser;
    @TableField
//    @NotNull
    private Integer loginStatus;
    @TableField
    private String loginMessage;
    @TableField
    private String accessToken;

    //
    // 非数据库字段
    // ------------------------------------------------------------------------------

    //
    // getter/setter
    // ------------------------------------------------------------------------------

    public Long getAuditId() {
        return auditId;
    }

    public AuditLogin setAuditId(Long auditId) {
        this.auditId = auditId;
        return this;
    }

    public String getAuditType() {
        return auditType;
    }

    public AuditLogin setAuditType(String auditType) {
        this.auditType = auditType;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public AuditLogin setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public String getLoginName() {
        return loginName;
    }

    public AuditLogin setLoginName(String loginName) {
        this.loginName = loginName;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public AuditLogin setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public AuditLogin setEmail(String email) {
        this.email = email;
        return this;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public AuditLogin setTenantId(Long tenantId) {
        this.tenantId = tenantId;
        return this;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public AuditLogin setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
        return this;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public AuditLogin setLoginIp(String loginIp) {
        this.loginIp = loginIp;
        return this;
    }

    public String getLoginClient() {
        return loginClient;
    }

    public AuditLogin setLoginClient(String loginClient) {
        this.loginClient = loginClient;
        return this;
    }

    public String getLoginPlatform() {
        return loginPlatform;
    }

    public AuditLogin setLoginPlatform(String loginPlatform) {
        this.loginPlatform = loginPlatform;
        return this;
    }

    public String getLoginOs() {
        return loginOs;
    }

    public AuditLogin setLoginOs(String loginOs) {
        this.loginOs = loginOs;
        return this;
    }

    public String getLoginBrowser() {
        return loginBrowser;
    }

    public AuditLogin setLoginBrowser(String loginBrowser) {
        this.loginBrowser = loginBrowser;
        return this;
    }

    public Integer getLoginStatus() {
        return loginStatus;
    }

    public AuditLogin setLoginStatus(Integer loginStatus) {
        this.loginStatus = loginStatus;
        return this;
    }

    public String getLoginMessage() {
        return loginMessage;
    }

    public AuditLogin setLoginMessage(String loginMessage) {
        this.loginMessage = loginMessage;
        return this;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public AuditLogin setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }
}
