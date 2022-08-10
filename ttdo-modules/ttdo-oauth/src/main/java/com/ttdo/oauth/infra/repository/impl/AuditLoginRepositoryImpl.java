package com.ttdo.oauth.infra.repository.impl;

import com.ttdo.oauth.domain.repository.AuditLoginRepository;
import com.ttdo.oauth.infra.mapper.AuditLoginMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuditLoginRepositoryImpl implements AuditLoginRepository {

    @Autowired
    private AuditLoginMapper auditLoginMapper;


}
