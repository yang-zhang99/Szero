package com.me.oauth.infra.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.me.oauth.domain.entity.AuditLogin;
import org.apache.ibatis.annotations.Mapper;

/**
 * 登录日志审计Mapper
 *
 * @author xingxing.wu@hand-china.com 2018-12-26 15:17:47
 */
@Mapper
public interface AuditLoginMapper extends BaseMapper<AuditLogin> {

}
