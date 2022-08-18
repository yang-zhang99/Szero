package com.me.gateway.helper.infra.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.tomcat.util.security.PermissionCheck;

/**
 * 缺失权限Mapper
 *
 * @author KAIBING.JIANG@HAND-CHINA.COM
 */
@Mapper
public interface PermissionCheckMapper extends BaseMapper<PermissionCheck> {

}
