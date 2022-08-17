package com.me.oauth.infra.encrypt.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * 加密VO
 *
 * @author bojiangzhou 2019/12/05
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EncryptVO implements Serializable {
    private static final long serialVersionUID = -4146843443534527356L;

    private String publicKey;
    private String privateKey;



    public String getPublicKey() {
        return publicKey;
    }

    public EncryptVO setPublicKey(String publicKey) {
        this.publicKey = publicKey;
        return this;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public EncryptVO setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
        return this;
    }
}
