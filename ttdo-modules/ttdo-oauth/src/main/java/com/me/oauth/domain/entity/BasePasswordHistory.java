package com.me.oauth.domain.entity;

//
//import javax.persistence.*;

import com.baomidou.mybatisplus.annotation.OrderBy;
import com.baomidou.mybatisplus.annotation.TableName;
import com.me.core.mybatis.AuditDomain;
import org.springframework.data.annotation.Id;

/**
 * @author wuguokai
 */

@TableName(value = "oauth_password_history")
public class BasePasswordHistory extends AuditDomain {

    public static final String FIELD_ID = "id";
    public static final String FIELD_USER_ID = "userId";

    @Id
//    @GeneratedValue
    @OrderBy
    private Long id;
//    @Where
    private Long userId;
//    @Column(name = "hash_password")
    private String password;
    private Long tenantId;

    public BasePasswordHistory() {
    }

    public BasePasswordHistory(Long userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public BasePasswordHistory setTenantId(Long tenantId) {
        this.tenantId = tenantId;
        return this;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
