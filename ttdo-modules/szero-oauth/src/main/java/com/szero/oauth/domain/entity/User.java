//package com.szero.oauth.domain.entity;
//
//import javax.persistence.*;
//import javax.validation.constraints.NotNull;
//import java.time.Instant;
//
//@Entity
//@Table(name = "iam_user")
//public class User {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id", nullable = false)
//    private Long id;
//
//    @Column(name = "login_name", nullable = false, length = 128)
//    private String loginName;
//
//    @Column(name = "email", length = 128)
//    private String email;
//
//    @Column(name = "organization_id", nullable = false)
//    private Long organizationId;
//
//    @Column(name = "HASH_PASSWORD", length = 128)
//    private String hashPassword;
//
//    @Column(name = "real_name", length = 128)
//    private String realName;
//
//    @Column(name = "phone", length = 32)
//    private String phone;
//
//    @Column(name = "INTERNATIONAL_TEL_CODE", length = 16)
//    private String internationalTelCode;
//
//    @Column(name = "image_url", length = 480)
//    private String imageUrl;
//
//    @Lob
//    @Column(name = "profile_photo")
//    private String profilePhoto;
//
//    @Column(name = "language", nullable = false, length = 16)
//    private String language;
//
//    @Column(name = "time_zone", nullable = false, length = 16)
//    private String timeZone;
//
//    @Column(name = "last_password_updated_at", nullable = false)
//    private Instant lastPasswordUpdatedAt;
//
//    @Column(name = "last_login_at")
//    private Instant lastLoginAt;
//
//    @Column(name = "is_enabled", nullable = false)
//    private Integer isEnabled;
//
//    @Column(name = "is_locked", nullable = false)
//    private Integer isLocked;
//
//    @Column(name = "is_ldap")
//    private Integer isLdap;
//
//    @Column(name = "is_admin")
//    private Integer isAdmin;
//
//    @Column(name = "locked_until_at")
//    private Instant lockedUntilAt;
//
//    @Column(name = "password_attempt")
//    private Integer passwordAttempt;
//
//    @Column(name = "object_version_number")
//    private Long objectVersionNumber;
//
//    @Column(name = "created_by")
//    private Long createdBy;
//
//    @Column(name = "creation_date")
//    private Instant creationDate;
//
//    @Column(name = "last_updated_by")
//    private Long lastUpdatedBy;
//
//    @Column(name = "last_update_date")
//    private Instant lastUpdateDate;
//
//    @Column(name = "user_type", nullable = false, length = 30)
//    private String userType;
//
//    @Column(name = "attribute1", length = 150)
//    private String attribute1;
//
//    @Column(name = "attribute2", length = 150)
//    private String attribute2;
//
//    @Column(name = "attribute3", length = 150)
//    private String attribute3;
//
//    @Column(name = "attribute4", length = 150)
//    private String attribute4;
//
//    @Column(name = "attribute5", length = 150)
//    private String attribute5;
//
//    @Column(name = "attribute6", length = 150)
//    private String attribute6;
//
//    @Column(name = "attribute7", length = 150)
//    private String attribute7;
//
//    @Column(name = "attribute8", length = 150)
//    private String attribute8;
//
//    @Column(name = "attribute9", length = 150)
//    private String attribute9;
//
//    @Column(name = "attribute10", length = 150)
//    private String attribute10;
//
//    @Column(name = "attribute11", length = 150)
//    private String attribute11;
//
//    @Column(name = "attribute12", length = 150)
//    private String attribute12;
//
//    @Column(name = "attribute13", length = 150)
//    private String attribute13;
//
//    @Column(name = "attribute14", length = 150)
//    private String attribute14;
//
//    @Column(name = "attribute15", length = 150)
//    private String attribute15;
//
//    public String getAttribute15() {
//        return attribute15;
//    }
//
//    public void setAttribute15(String attribute15) {
//        this.attribute15 = attribute15;
//    }
//
//    public String getAttribute14() {
//        return attribute14;
//    }
//
//    public void setAttribute14(String attribute14) {
//        this.attribute14 = attribute14;
//    }
//
//    public String getAttribute13() {
//        return attribute13;
//    }
//
//    public void setAttribute13(String attribute13) {
//        this.attribute13 = attribute13;
//    }
//
//    public String getAttribute12() {
//        return attribute12;
//    }
//
//    public void setAttribute12(String attribute12) {
//        this.attribute12 = attribute12;
//    }
//
//    public String getAttribute11() {
//        return attribute11;
//    }
//
//    public void setAttribute11(String attribute11) {
//        this.attribute11 = attribute11;
//    }
//
//    public String getAttribute10() {
//        return attribute10;
//    }
//
//    public void setAttribute10(String attribute10) {
//        this.attribute10 = attribute10;
//    }
//
//    public String getAttribute9() {
//        return attribute9;
//    }
//
//    public void setAttribute9(String attribute9) {
//        this.attribute9 = attribute9;
//    }
//
//    public String getAttribute8() {
//        return attribute8;
//    }
//
//    public void setAttribute8(String attribute8) {
//        this.attribute8 = attribute8;
//    }
//
//    public String getAttribute7() {
//        return attribute7;
//    }
//
//    public void setAttribute7(String attribute7) {
//        this.attribute7 = attribute7;
//    }
//
//    public String getAttribute6() {
//        return attribute6;
//    }
//
//    public void setAttribute6(String attribute6) {
//        this.attribute6 = attribute6;
//    }
//
//    public String getAttribute5() {
//        return attribute5;
//    }
//
//    public void setAttribute5(String attribute5) {
//        this.attribute5 = attribute5;
//    }
//
//    public String getAttribute4() {
//        return attribute4;
//    }
//
//    public void setAttribute4(String attribute4) {
//        this.attribute4 = attribute4;
//    }
//
//    public String getAttribute3() {
//        return attribute3;
//    }
//
//    public void setAttribute3(String attribute3) {
//        this.attribute3 = attribute3;
//    }
//
//    public String getAttribute2() {
//        return attribute2;
//    }
//
//    public void setAttribute2(String attribute2) {
//        this.attribute2 = attribute2;
//    }
//
//    public String getAttribute1() {
//        return attribute1;
//    }
//
//    public void setAttribute1(String attribute1) {
//        this.attribute1 = attribute1;
//    }
//
//    public String getUserType() {
//        return userType;
//    }
//
//    public void setUserType(String userType) {
//        this.userType = userType;
//    }
//
//    public Instant getLastUpdateDate() {
//        return lastUpdateDate;
//    }
//
//    public void setLastUpdateDate(Instant lastUpdateDate) {
//        this.lastUpdateDate = lastUpdateDate;
//    }
//
//    public Long getLastUpdatedBy() {
//        return lastUpdatedBy;
//    }
//
//    public void setLastUpdatedBy(Long lastUpdatedBy) {
//        this.lastUpdatedBy = lastUpdatedBy;
//    }
//
//    public Instant getCreationDate() {
//        return creationDate;
//    }
//
//    public void setCreationDate(Instant creationDate) {
//        this.creationDate = creationDate;
//    }
//
//    public Long getCreatedBy() {
//        return createdBy;
//    }
//
//    public void setCreatedBy(Long createdBy) {
//        this.createdBy = createdBy;
//    }
//
//    public Long getObjectVersionNumber() {
//        return objectVersionNumber;
//    }
//
//    public void setObjectVersionNumber(Long objectVersionNumber) {
//        this.objectVersionNumber = objectVersionNumber;
//    }
//
//    public Integer getPasswordAttempt() {
//        return passwordAttempt;
//    }
//
//    public void setPasswordAttempt(Integer passwordAttempt) {
//        this.passwordAttempt = passwordAttempt;
//    }
//
//    public Instant getLockedUntilAt() {
//        return lockedUntilAt;
//    }
//
//    public void setLockedUntilAt(Instant lockedUntilAt) {
//        this.lockedUntilAt = lockedUntilAt;
//    }
//
//    public Integer getIsAdmin() {
//        return isAdmin;
//    }
//
//    public void setIsAdmin(Integer isAdmin) {
//        this.isAdmin = isAdmin;
//    }
//
//    public Integer getIsLdap() {
//        return isLdap;
//    }
//
//    public void setIsLdap(Integer isLdap) {
//        this.isLdap = isLdap;
//    }
//
//    public Integer getIsLocked() {
//        return isLocked;
//    }
//
//    public void setIsLocked(Integer isLocked) {
//        this.isLocked = isLocked;
//    }
//
//    public Integer getIsEnabled() {
//        return isEnabled;
//    }
//
//    public void setIsEnabled(Integer isEnabled) {
//        this.isEnabled = isEnabled;
//    }
//
//    public Instant getLastLoginAt() {
//        return lastLoginAt;
//    }
//
//    public void setLastLoginAt(Instant lastLoginAt) {
//        this.lastLoginAt = lastLoginAt;
//    }
//
//    public Instant getLastPasswordUpdatedAt() {
//        return lastPasswordUpdatedAt;
//    }
//
//    public void setLastPasswordUpdatedAt(Instant lastPasswordUpdatedAt) {
//        this.lastPasswordUpdatedAt = lastPasswordUpdatedAt;
//    }
//
//    public String getTimeZone() {
//        return timeZone;
//    }
//
//    public void setTimeZone(String timeZone) {
//        this.timeZone = timeZone;
//    }
//
//    public String getLanguage() {
//        return language;
//    }
//
//    public void setLanguage(String language) {
//        this.language = language;
//    }
//
//    public String getProfilePhoto() {
//        return profilePhoto;
//    }
//
//    public void setProfilePhoto(String profilePhoto) {
//        this.profilePhoto = profilePhoto;
//    }
//
//    public String getImageUrl() {
//        return imageUrl;
//    }
//
//    public void setImageUrl(String imageUrl) {
//        this.imageUrl = imageUrl;
//    }
//
//    public String getInternationalTelCode() {
//        return internationalTelCode;
//    }
//
//    public void setInternationalTelCode(String internationalTelCode) {
//        this.internationalTelCode = internationalTelCode;
//    }
//
//    public String getPhone() {
//        return phone;
//    }
//
//    public void setPhone(String phone) {
//        this.phone = phone;
//    }
//
//    public String getRealName() {
//        return realName;
//    }
//
//    public void setRealName(String realName) {
//        this.realName = realName;
//    }
//
//    public String getHashPassword() {
//        return hashPassword;
//    }
//
//    public void setHashPassword(String hashPassword) {
//        this.hashPassword = hashPassword;
//    }
//
//    public Long getOrganizationId() {
//        return organizationId;
//    }
//
//    public void setOrganizationId(Long organizationId) {
//        this.organizationId = organizationId;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getLoginName() {
//        return loginName;
//    }
//
//    public void setLoginName(String loginName) {
//        this.loginName = loginName;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//}