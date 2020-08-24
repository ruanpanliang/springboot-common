package com.lc.springboot.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lc.springboot.user.dto.response.PrivilegeLoginDetailResponse;
import com.lc.springboot.user.model.Privilege;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * 权限 Mapper 接口
 *
 * @author: liangc
 * @date: 2020-08-17 17:18
 * @version 1.0
 */
public interface PrivilegeMapper extends BaseMapper<Privilege> {

  List<PrivilegeLoginDetailResponse> getPrivilegeListByRoleIds(
      @Param("idCollection") Collection<Long> idCollection);
}
