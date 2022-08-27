package com.me.oauth.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.me.core.mybatis.AuditDomain;
import org.springframework.data.annotation.Id;

import java.util.Date;

/**
 *
 * @author bojiangzhou 2019/08/07
 */
@TableName(value = "hiam_user_info")
public class BaseUserInfo extends AuditDomain {

    public static final String FIELD_USER_ID = "userId";
    public static final String FIELD_SECURITY_LEVEL_CODE = "securityLevelCode";
    public static final String FIELD_PASSWORD_RESET_FLAG = "passwordResetFlag";
    public static final String FIELD_LOCKED_DATE = "lockedDate";

    @Id
    private Long userId;
    private String securityLevelCode;
    private Integer passwordResetFlag;
    private Date lockedDate;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getSecurityLevelCode() {
        return securityLevelCode;
    }

    public void setSecurityLevelCode(String securityLevelCode) {
        this.securityLevelCode = securityLevelCode;
    }

    public Integer getPasswordResetFlag() {
        return passwordResetFlag;
    }

    public void setPasswordResetFlag(Integer passwordResetFlag) {
        this.passwordResetFlag = passwordResetFlag;
    }

    public Date getLockedDate() {
        return lockedDate;
    }

    public void setLockedDate(Date lockedDate) {
        this.lockedDate = lockedDate;
    }
}
