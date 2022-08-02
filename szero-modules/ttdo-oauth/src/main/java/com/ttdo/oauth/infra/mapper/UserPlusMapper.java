package com.ttdo.oauth.infra.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ttdo.oauth.domain.entity.User;

public interface UserPlusMapper extends BaseMapper<User> {



    User selectLoginUser(User params);

}
