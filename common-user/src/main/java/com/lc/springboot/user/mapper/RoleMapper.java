package com.lc.springboot.user.mapper;

import com.lc.springboot.user.dto.response.RoleLoginDetailResponse;
import org.apache.ibatis.annotations.Param;

import com.lc.springboot.user.model.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * 角色 Mapper 接口
 *
 * @author: liangc
 * @date: 2020-08-17 17:18
 * @version 1.0
 */
public interface RoleMapper extends BaseMapper<Role> {

  /**
   * 根据用户编号获取用户对应的角色列表信息
   *
   * @param userId 用户编号
   * @return 角色列表信息
   */
  List<RoleLoginDetailResponse> getRoleListByUserId(@Param("userId") Long userId);
}
