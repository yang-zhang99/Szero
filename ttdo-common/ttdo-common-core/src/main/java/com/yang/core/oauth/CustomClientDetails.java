package com.yang.core.oauth;


import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;

import java.util.List;
import java.util.Objects;

public class CustomClientDetails extends BaseClientDetails {
    private static final long serialVersionUID = 4647677232738772939L;
    private Long id;
    private Long organizationId;
    private Long currentRoleId;
    private Long currentTenantId;
    private List<Long> roleIds;
    private List<Long> tenantIds;
    private String timeZone;
    private Integer apiEncryptFlag;

    public CustomClientDetails() {
    }

    public Long getId() {
        return this.id;
    }

    public CustomClientDetails setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getOrganizationId() {
        return this.organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public List<Long> getRoleIds() {
        return this.roleIds;
    }

    public void setRoleIds(List<Long> roleIds) {
        this.roleIds = roleIds;
    }

    public List<Long> getTenantIds() {
        return this.tenantIds;
    }

    public void setTenantIds(List<Long> tenantIds) {
        this.tenantIds = tenantIds;
    }

    public Long getCurrentRoleId() {
        return this.currentRoleId;
    }

    public CustomClientDetails setCurrentRoleId(Long currentRoleId) {
        this.currentRoleId = currentRoleId;
        return this;
    }

    public Long getCurrentTenantId() {
        return this.currentTenantId;
    }

    public CustomClientDetails setCurrentTenantId(Long currentTenantId) {
        this.currentTenantId = currentTenantId;
        return this;
    }

    public String getTimeZone() {
        return this.timeZone;
    }

    public CustomClientDetails setTimeZone(String timeZone) {
        this.timeZone = timeZone;
        return this;
    }

    public Integer getApiEncryptFlag() {
        return this.apiEncryptFlag;
    }

    public CustomClientDetails setApiEncryptFlag(Integer apiEncryptFlag) {
        this.apiEncryptFlag = apiEncryptFlag;
        return this;
    }

    public String toJSONString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            if (!super.equals(o)) {
                return false;
            } else {
                CustomClientDetails that = (CustomClientDetails) o;
                return Objects.equals(this.id, that.id) && Objects.equals(this.organizationId, that.organizationId) && Objects.equals(this.currentRoleId, that.currentRoleId) && Objects.equals(this.currentTenantId, that.currentTenantId) && Objects.equals(this.roleIds, that.roleIds) && Objects.equals(this.tenantIds, that.tenantIds) && Objects.equals(this.timeZone, that.timeZone) && Objects.equals(this.apiEncryptFlag, that.apiEncryptFlag);
            }
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(new Object[]{super.hashCode(), this.id, this.organizationId, this.currentRoleId, this.currentTenantId, this.roleIds, this.tenantIds, this.timeZone, this.apiEncryptFlag});
    }

    public String toString() {
        return "CustomClientDetails{id=" + this.id + ", organizationId=" + this.organizationId + ", currentRoleId=" + this.currentRoleId + ", currentTenantId=" + this.currentTenantId + ", roleIds=" + this.roleIds + ", tenantIds=" + this.tenantIds + ", timeZone='" + this.timeZone + '\'' + ", apiEncryptFlag='" + this.apiEncryptFlag + '}';
    }
}
