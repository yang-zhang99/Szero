package com.me.oauth.infra.repository.impl;

import com.me.oauth.domain.entity.Client;
import com.me.oauth.domain.repository.ClientRepository;
import com.me.oauth.domain.vo.ClientRoleDetails;
import com.me.oauth.infra.mapper.ClientPlusMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ClientRepositoryImpl implements ClientRepository {

    @Autowired
    private ClientPlusMapper clientPlusMapper;

    @Override
    public Client selectClient(String clientName) {
        return clientPlusMapper.selectClientWithUserAndRole(clientName);
    }

    @Override
    public List<ClientRoleDetails> selectRoleDetails(Long clientId) {
        return clientPlusMapper.selectRoleDetails(clientId);
    }

}
