package com.ttdo.oauth.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ttdo.core.mybatis.AuditDomain;
import com.ttdo.oauth.domain.vo.Role;

import java.io.Serializable;
import java.util.List;

/**
 * 客户端
 */

@TableName(value = "oauth_client")
public class Client extends AuditDomain implements Serializable {
    private static final long serialVersionUID = -8770569494296714270L;

    @TableId(type = IdType.AUTO)
    private Long id;
    @TableField
    private String name;
    @TableField
    private Long organizationId;
    private String resourceIds;
    private String secret;
    private String scope;
    private String authorizedGrantTypes;
    private String webServerRedirectUri;
    private Long accessTokenValidity;
    private Long refreshTokenValidity;
    private String additionalInformation;
    private String autoApprove;
    private Integer enabledFlag;
    private String accessRoles;
    private Integer pwdReplayFlag;
    @TableField(value = "time_zone")
    private String clientTimeZone;

    //
    // user info
    // ------------------------------------------------------------------------------
    @TableField(exist = false)
    private Long userId;
    @TableField(exist = false)
    private String realName;
    @TableField(exist = false)
    private String email;
    @TableField(exist = false)
    private String timeZone;
    @TableField(exist = false)
    private String language;
    @TableField(exist = false)
    private Boolean isAdmin;
    @TableField(exist = false)
    private List<Role> roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public String getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(String resourceIds) {
        this.resourceIds = resourceIds;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getAuthorizedGrantTypes() {
        return authorizedGrantTypes;
    }

    public void setAuthorizedGrantTypes(String authorizedGrantTypes) {
        this.authorizedGrantTypes = authorizedGrantTypes;
    }

    public String getWebServerRedirectUri() {
        return webServerRedirectUri;
    }

    public void setWebServerRedirectUri(String webServerRedirectUri) {
        this.webServerRedirectUri = webServerRedirectUri;
    }

    public Long getAccessTokenValidity() {
        return accessTokenValidity;
    }

    public void setAccessTokenValidity(Long accessTokenValidity) {
        this.accessTokenValidity = accessTokenValidity;
    }

    public Long getRefreshTokenValidity() {
        return refreshTokenValidity;
    }

    public void setRefreshTokenValidity(Long refreshTokenValidity) {
        this.refreshTokenValidity = refreshTokenValidity;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    public String getAutoApprove() {
        return autoApprove;
    }

    public void setAutoApprove(String autoApprove) {
        this.autoApprove = autoApprove;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public Integer getEnabledFlag() {
        return enabledFlag;
    }

    public void setEnabledFlag(Integer enabledFlag) {
        this.enabledFlag = enabledFlag;
    }

    public String getAccessRoles() {
        return accessRoles;
    }

    public Client setAccessRoles(String accessRoles) {
        this.accessRoles = accessRoles;
        return this;
    }

    public Integer getPwdReplayFlag() {
        return pwdReplayFlag;
    }

    public Client setPwdReplayFlag(Integer pwdReplayFlag) {
        this.pwdReplayFlag = pwdReplayFlag;
        return this;
    }

    public String getClientTimeZone() {
        return clientTimeZone;
    }

    public Client setClientTimeZone(String clientTimeZone) {
        this.clientTimeZone = clientTimeZone;
        return this;
    }
}
