package com.me.core.mybatis;


import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * <p>
 * 安全令牌
 * </p>
 */
public interface SecurityToken {

    /**
     * 获取 token
     *
     * @return token
     */
    String get_token();

    /**
     * 设置 token
     *
     * @param tokenValue token 值
     */
    void set_token(String tokenValue);

    /**
     * 返回关联的实体类型，自定义 DTO 可以重写这个方法，返回对应的<strong>实体类型</strong>
     *
     * @return 实体类型
     */
    @JsonIgnore
    default Class<? extends SecurityToken> associateEntityClass() {
        return null;
    }
}
