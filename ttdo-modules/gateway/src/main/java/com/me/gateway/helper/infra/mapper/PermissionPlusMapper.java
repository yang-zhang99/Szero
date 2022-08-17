package com.me.gateway.helper.infra.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.me.gateway.helper.domain.PermissionCheckDTO;
import com.me.gateway.helper.domain.vo.RoleVO;
import com.me.gateway.helper.domain.entity.PermissionDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author qingsheng.chen 2018/12/26 星期三 10:46
 */
@Mapper
public interface PermissionPlusMapper extends BaseMapper<PermissionDO> {


    List<Long> selectSourceIdsByMemberAndRole(PermissionCheckDTO query);

    List<PermissionDO> selectPermissionByMethodAndService(@Param("method") String method, @Param("service") String service);

    int countMenuPermission(@Param("menuId") Long menuId, @Param("permissionCode") String permissionCode);

    /**
     * 查询系统超级管理员
     * {@link org.hzero.common.HZeroConstant.RoleCode#SITE} &
     * {@link org.hzero.common.HZeroConstant.RoleCode#TENANT}
     */
    List<RoleVO> selectSuperAdminRole();

    int countAvailableRole(@Param("memberId") Long memberId, @Param("memberType") String memberType, @Param("roleId") Long roleId);
}
