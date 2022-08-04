package com.ttdo.oauth.infra.repository.impl;

import com.ttdo.oauth.domain.entity.Client;
import com.ttdo.oauth.domain.repository.ClientRepository;
import com.ttdo.oauth.domain.vo.ClientRoleDetails;
import com.ttdo.oauth.infra.mapper.ClientPlusMapper;
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
