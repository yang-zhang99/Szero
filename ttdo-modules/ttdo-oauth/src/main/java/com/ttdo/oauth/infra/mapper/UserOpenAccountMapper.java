package com.ttdo.oauth.infra.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ttdo.oauth.domain.entity.UserOpenAccount;
import com.ttdo.oauth.security.service.UserDetailsBuilder;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author jiaxu.cui@hand-china.com 2018/10/15 17:07
 */
@Mapper
public interface UserOpenAccountMapper extends BaseMapper<UserOpenAccount> {


    List<UserOpenAccount> selectListByUserOpenAccount (UserOpenAccount userOpenAccount);

}
