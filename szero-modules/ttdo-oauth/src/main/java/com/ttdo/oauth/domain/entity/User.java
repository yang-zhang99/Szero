package com.ttdo.oauth.domain.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ttdo.oauth.security.constant.LoginField;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
@TableName("iam_user")
public class User {

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

    private Date lockedUntilAt;

    private Integer passwordAttempt;

    @TableField(value = "is_admin")
    private Boolean admin;

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
