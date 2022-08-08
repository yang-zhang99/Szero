package com.ttdo.oauth.infra.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ttdo.oauth.domain.entity.BasePasswordPolicy;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface BasePasswordPolicyMapper extends BaseMapper<BasePasswordPolicy> {
}
