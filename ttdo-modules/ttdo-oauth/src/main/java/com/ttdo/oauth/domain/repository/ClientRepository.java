package com.ttdo.oauth.domain.repository;

import com.ttdo.oauth.domain.entity.Client;
import com.ttdo.oauth.domain.vo.ClientRoleDetails;

import java.util.List;

/**
 * 客户端资源库
 *
 */
public interface ClientRepository  {

    /**
     * 根据客户端名称查询客户端
     *
     * @param clientName 客户端唯一名称
     * @return Client
     */
    Client selectClient(String clientName);

    /**
     * 查询客户端可选角色
     *
     * @param clientId 客户端ID
     * @return 可选角色
     */
    List<ClientRoleDetails> selectRoleDetails(Long clientId);

}

