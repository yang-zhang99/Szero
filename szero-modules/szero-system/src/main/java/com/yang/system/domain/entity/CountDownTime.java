package com.yang.system.domain.entity;

import java.util.Date;

public class CountDownTime extends AuditDomain {

    private Long id;

    private String targetName;


    private Date expirationDate;

    private boolean isMust;


    private int enabledFlag;

    private Long userId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public boolean isMust() {
        return isMust;
    }

    public void setMust(boolean must) {
        isMust = must;
    }

    public int getEnabledFlag() {
        return enabledFlag;
    }

    public void setEnabledFlag(int enabledFlag) {
        this.enabledFlag = enabledFlag;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
