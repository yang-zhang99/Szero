package com.me.oauth.infra.repository.impl;

import com.me.oauth.domain.repository.AuditLoginRepository;
import com.me.oauth.infra.mapper.AuditLoginMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuditLoginRepositoryImpl implements AuditLoginRepository {

    @Autowired
    private AuditLoginMapper auditLoginMapper;


}
