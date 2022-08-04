package com.ttdo.oauth.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 语言
 */
//@MultiLanguage
@TableName(value = "fd_language")
public class Language {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String code;
    private String name;
    //    @MultiLanguageField
    private String description;

    public Long getId() {
        return id;
    }

    public Language setId(Long id) {
        this.id = id;
        return this;
    }

    public String getCode() {
        return code;
    }

    public Language setCode(String code) {
        this.code = code;
        return this;
    }

    public String getName() {
        return name;
    }

    public Language setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Language setDescription(String description) {
        this.description = description;
        return this;
    }

}
