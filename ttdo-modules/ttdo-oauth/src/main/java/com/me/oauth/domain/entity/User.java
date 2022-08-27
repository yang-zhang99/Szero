package com.me.oauth.domain.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.me.oauth.security.constant.LoginField;
import com.me.core.mybatis.AuditDomain;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

/**
 * 用户
 */
@TableName(value = "iam_user")
@Data
public class User extends AuditDomain implements Serializable {

    private static final long serialVersionUID = 1280333995775842225L;

    public static final String FIELD_PASSWORD = "password";
    public static final String FIELD_LOCKED = "locked";
    public static final String FIELD_LOCKED_DATE = "locked_date";
    public static final String FIELD_LOCKED_UNTIL_AT = "lockedUntilAt";
    public static final String FIELD_LAST_PASSWORD_UPDATED_AT = "lastPasswordUpdatedAt";

    /**
     * 线程安全的，放心用
     */
    private static final BCryptPasswordEncoder ENCODER = new BCryptPasswordEncoder();

    /**
     * 加密密码的同时，检测密码安全度
     */
    public void encodePassword() {
        this.password = ENCODER.encode(this.getPassword());
    }

    public void unlocked() {
        this.locked = false;
        this.lockedDate = null;
        this.lockedUntilAt = null;
    }

    @TableId(type = IdType.AUTO)
    private Long id;


    @TableField
    private String loginName;
    @TableField
    private String email;
    @TableField
    private Long organizationId;

    @TableField(value = "hash_password")
    private String password;
    @TableField
    private String realName;
    @TableField
    private String phone;
    @TableField
    private String internationalTelCode;
    @TableField
    private String imageUrl;
    @TableField
    private String profilePhoto;
    @TableField
    private String language;
    @TableField
    private String timeZone;
    @TableField
    private Date lastPasswordUpdatedAt;
    @TableField
    private Date lastLoginAt;

    @TableField(value = "is_enabled")
    private Boolean enabled;

    @TableField(value = "is_locked")
    private Boolean locked;

    @TableField(value = "is_ldap")
    private Boolean ldap;
    @TableField

    private Date lockedUntilAt;
    @TableField

    private Integer passwordAttempt;

    @TableField(value = "is_admin")
    private Boolean admin;
    @TableField

    private String userType;

    // ===============================================================================
    // 以下字段为UserInfo的字段，User与UserInfo是一对一关系
    // ===============================================================================
    @TableField(exist = false)
    private Date lockedDate;
    @TableField(exist = false)

    private Long employeeId;
    @TableField(exist = false)

    private LocalDate startDateActive;
    @TableField(exist = false)

    private LocalDate endDateActive;
    @TableField(exist = false)

    private Integer phoneCheckFlag;
    @TableField(exist = false)

    private Integer emailCheckFlag;
    @TableField(exist = false)

    private Long defaultCompanyId;
    @TableField(exist = false)

    private LocalDate birthday;
    @TableField(exist = false)

    private String nickname;
    @TableField(exist = false)
    private Integer gender;
    @TableField(exist = false)
    private Long countryId;
    @TableField(exist = false)
    private Long regionId;
    @TableField(exist = false)
    private Integer passwordResetFlag;

    //
    // 额外查询字段
    // ------------------------------------------------------------------------------

    @TableField(exist = false)
    @JsonIgnore
    private Long tenantId;
    @TableField(exist = false)
    private String tenantName;
    @TableField(exist = false)
    @JsonIgnore
    private Integer tenantEnabledFlag;
    /**
     * 使用哪个字段登录的
     */
    @TableField(exist = false)
    @JsonIgnore
    private LoginField loginField;

}
