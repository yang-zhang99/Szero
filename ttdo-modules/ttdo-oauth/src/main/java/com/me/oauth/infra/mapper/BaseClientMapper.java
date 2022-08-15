package com.me.oauth.infra.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.me.oauth.domain.entity.BaseClient;
import org.apache.ibatis.annotations.Mapper;

@Mapper

public interface BaseClientMapper extends BaseMapper<BaseClient> {
}
